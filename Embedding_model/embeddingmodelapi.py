from fastapi import FastAPI, Request
from pydantic import BaseModel
from sentence_transformers import SentenceTransformer
import uvicorn

# Load model
model = SentenceTransformer('all-MiniLM-L6-v2')

app = FastAPI()

class InputText(BaseModel):
    text: str

@app.post("/embed")
async def get_embedding(data: InputText):
    embedding = model.encode(data.text).tolist()
    return {"embedding": embedding}

# Run on 9092
if __name__ == "__main__":
    uvicorn.run("embeddingmodelapi:app", host="0.0.0.0", port=8000)
