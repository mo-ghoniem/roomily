import requests
from bs4 import BeautifulSoup
from typing import List, Dict, Optional

import roomily_property_db as rdb





class PropertyScraper:
    locations_dict = {
        'usa': '5460aeac1d0f1',
        'united states': '5460aeac1d0f1',
        'egypt': '53d0ddf4f0904',
        'eg': '53d0ddf4f0904',
        'france': '5460aeb1b0bf2',
        'fr': '5460aeb1b0bf2',
        'brazil': '53d0e1e58ac2c',
        'bra': '53d0e1e58ac2c',
        'england': '5460aeaded08a',
        'eng': '5460aeaded08a',
        'germany': '5460aecab790d',
        'ger': '5460aecab790d',
        'italy': '5460aeae078f7',
        'ita': '5460aeae078f7'
    }

    def __init__(self, cache_manager: rdb.CacheManager=rdb.CacheManager()):
        """
        Initialize the PropertyScraper with a CacheManager instance.
        """
        self.cache_manager = cache_manager

    def scrape_properties_data(self, location: str = 'egypt') -> List[Dict[str, Optional[str]]]:
        """
        Scrapes property data from HomeToGo based on the given location.
        First checks the cache, if data is not found it scrapes and caches it.
        """

        location_id = self.locations_dict.get(location.lower(), '53d0ddf4f0904')

        # First check if the data is in the cache
        cached_data = self.cache_manager.get_cached_properties(location_id)
        if cached_data:
            print("Using cached data...")
            return cached_data

        

        try:
            # Send a request to get the search page
            page = requests.get(f'https://www.hometogo.com/search/{location_id}')
            page.raise_for_status()  # Raise an HTTPError for bad responses (4xx or 5xx)

            # Parse the page content with BeautifulSoup
            soup = BeautifulSoup(page.content, 'lxml')

            # Extract the search ID from the page
            search_id = soup.select_one("#search-preload")['href']
            if not search_id:
                raise ValueError("Search ID not found in the page.")

            # Send a request to get the JSON data with the property listings
            properties_json = requests.get(f'https://www.hometogo.com{search_id}').json()

            # Extract the properties data
            properties_data = properties_json.get('offers', [])
            if not properties_data:
                raise ValueError("No property data found in the JSON response.")

            # Process the properties data and build a list of property dictionaries
            properties_list = [{
                'id': data.get('id', '-'),
                'title': data.get('title', '-'),
                'price': data.get('lowestPriceInfo', {}).get('display', '-'),
                'location': data.get('locationTrailHeader', '-'),
                'geoLocation': data.get('geoLocation', '-'),
                'imageLinks': data.get('imageLinks', {}).get('medium', '-'),
                'amenities': [amenity['label'] for amenity in data.get('amenities', {}).get('common', [])]
            } for data in properties_data]

            # Cache the scraped data
            self.cache_manager.cache_properties(properties_list, location_id)

            return properties_list

        except requests.exceptions.RequestException as e:
            print(f"Request error: {e}")
            return []

        except ValueError as e:
            print(f"Value error: {e}")
            return []

        except Exception as e:
            print(f"An error occurred: {e}")
            return []


# # Example Usage:
# if __name__ == '__main__':

#     # Initialize PropertyScraper with CacheManager
#     scraper = PropertyScraper()

#     # Scrape data for USA
#     properties = scraper.scrape_properties_data('usa')
#     print(properties[0])
