from sqlalchemy import create_engine, Column, String, Text, JSON, TIMESTAMP
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker
from sqlalchemy.exc import SQLAlchemyError
from typing import List, Dict, Optional

import logging

logging.getLogger('sqlalchemy.engine.Engine').disabled = True

Base = declarative_base()

# Define the database model for caching properties
class PropertyCache(Base):
    __tablename__ = 'properties'

    id = Column(String(255), primary_key=True)
    title = Column(Text)
    price = Column(Text)
    location = Column(Text)
    geoLocation = Column(JSON)
    imageLinks = Column(Text)
    amenities = Column(Text)  # Store amenities as a JSON array
    location_id = Column(String(255))
    last_scraped = Column(TIMESTAMP)

    def __repr__(self):
        return f"<Property(id={self.id}, title={self.title}, location={self.location})>"


class CacheManager:
    def __init__(self, db_url: str = 'sqlite:///db.sqlite3'):
        """
        Initialize the CacheManager with the provided SQLite database URL.
        Creates the database table if it doesn't exist.
        """
        self.db_url = db_url
        self.engine = create_engine(self.db_url, echo=True)
        self.Session = sessionmaker(bind=self.engine)
        
        # Create the properties table if it doesn't exist
        self.create_table()

    def create_table(self):
        """ Creates the properties table if it does not exist. """
        Base.metadata.create_all(self.engine)

    def get_cached_properties(self, location_id: str='53d0ddf4f0904') -> Optional[List[Dict[str, str]]]:
        """
        Retrieves properties from the cache if they exist.
        Returns cached data or None if not found.
        """
        session = self.Session()
        
        try:
            # Retrieve cached properties for the given location
            properties = session.query(PropertyCache).filter_by(location_id=location_id).order_by(PropertyCache.last_scraped.desc()).all()

            if properties:
                cached_data = [{
                'id': prop.id,
                'title': prop.title,
                'price': prop.price,
                'location': prop.location,
                'geoLocation': prop.geoLocation,
                'imageLinks': prop.imageLinks,
                'amenities': prop.amenities.split(',')
            } for prop in properties if prop.imageLinks!='-']
                return cached_data
            return []
        except SQLAlchemyError as e:
            print(f"Database error: {e}")
            return []
        finally:
            session.close()

    def cache_properties(self, properties: List[Dict[str, str]], location_id: str='53d0ddf4f0904'):
        """
        Caches the scraped properties into the database.
        """
        session = self.Session()

        try:
            # Insert or update properties in the cache
            for prop in properties:
                if prop['imageLinks']=='-':
                    continue
                cached_property = PropertyCache(
                    id=prop['id'],
                    title=prop['title'],
                    price=prop['price'],
                    location=prop['location'],
                    geoLocation=prop['geoLocation'],
                    imageLinks=prop['imageLinks'],
                    amenities=",".join(prop['amenities']),
                    location_id=location_id
                )
                
                # Add the property to the session
                session.merge(cached_property)  # Will update if the property exists, or insert a new one

            session.commit()
        except SQLAlchemyError as e:
            print(f"Database error: {e}")
            session.rollback()
        finally:
            session.close()