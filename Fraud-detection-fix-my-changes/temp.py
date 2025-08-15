from pymongo import MongoClient
from sqlalchemy import false

# Replace these values with your Atlas details
username = "rajkushwahofficial2025"
password = "America2024"
cluster = "cluster0.25jzqsf.mongodb.net"
database_name = "Fraud"
collection_name = "transactions"


# Connection string
uri = f"mongodb+srv://{username}:{password}@{cluster}/?retryWrites=true&w=majority"

# Connect to the client
client = MongoClient(uri)

# Access DB and Collection
db = client[database_name]
collection = db[collection_name]

# Example: Delete all documents where status == 'failed'
delete_query = {}


result = collection.delete_many(delete_query)

print(f"{result.deleted_count} documents deleted.")
