from fastapi import FastAPI, HTTPException
from roomily_scraper import PropertyScraper  

import logging

# Disable SQLAlchemy engine logs (if applicable)
logging.getLogger('sqlalchemy.engine.Engine').disabled = True

# Initialize FastAPI app with metadata for API documentation
app = FastAPI( 
    title="Property Scraper API",  # Title of the API, visible in the docs
    description="API to get property listings based on location.",  # Description of the API
    version="1.0.0"  # API version number
)

# Initialize PropertyScraper instance
# PropertyScraper is responsible for fetching listings (e.g., vacation rentals, apartments) 
scraper = PropertyScraper()  # This scraper is designed to fetch property listings


# Define the endpoint for scraping property listings based on location
@app.get("/get-properties/")
async def gather_properties_data(location: str):
    """
    Endpoint to gather property listings for a given location.

    This endpoint accepts a query parameter `location`, which can be a country, 
    city, or region, to search for available properties. The properties can include 
    various types of accommodations such as apartments, houses, or vacation rentals.

    Args:
        location (str): The location to search for properties. This can be a country (e.g., "USA"). The location will be used to filter property listings.
    
    Returns:
        dict: A dictionary containing the scraped property data, such as the type of property, price, location details, 
              and available amenities. The exact structure of the data will depend on the scraper's implementation.
    
    Raises:
        HTTPException: If an error occurs while scraping the property data (e.g., network issues, invalid data), 
                       the API will return a 500 Internal Server Error with the exception details.
    
    Example usage:
        GET /get-properties/?location=USA
        GET /get-properties/?location=Egypt
    """
    try:
        # Call the PropertyScraper to scrape property listings for the given location
        properties = scraper.scrape_properties_data(location.lower())

        # Return the scraped property data as JSON
        return properties
    
    except Exception as e:
        # Handle any exceptions by raising an HTTP 500 error with the message from the exception
        raise HTTPException(status_code=500, detail=f"Error occurred: {e}")

