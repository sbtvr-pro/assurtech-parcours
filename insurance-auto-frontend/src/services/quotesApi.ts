const API_BASE_URL =
  import.meta.env.VITE_API_URL?.replace(/\/$/, "") || "http://localhost:8082";

export type Quote = {
  quoteId: number;
  customerName: string;
  vehicleType: string;
  riskLevel: "HIGH" | "LOW" | string;
  monthlyPremium: number;
};

export type CreateQuotePayload = {
  name: string;
  car: string;
  age: number | null;
  claims: number;
};

async function parseJsonSafely(response: Response) {
  const text = await response.text();
  if (!text) return null;
  try {
    return JSON.parse(text);
  } catch {
    return null;
  }
}

export async function getQuotes(): Promise<Quote[]> {
  const response = await fetch(`${API_BASE_URL}/api/quotes`);
  if (!response.ok) throw new Error("Erreur lors du chargement des devis");
  return (await response.json()) as Quote[];
}

export async function createQuote(payload: CreateQuotePayload): Promise<void> {
  const response = await fetch(`${API_BASE_URL}/api/quotes`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(payload),
  });

  if (response.ok) return;

  const data = await parseJsonSafely(response);
  const message =
    (data && typeof data.error === "string" && data.error) ||
    "Problème inconnu";
  throw new Error(message);
}

export async function deleteAllQuotes(): Promise<void> {
  const response = await fetch(`${API_BASE_URL}/api/quotes`, { method: "DELETE" });
  if (!response.ok)
    throw new Error("Erreur lors de la suppression de l'historique");
}

export async function deleteQuoteById(quoteId: number): Promise<void> {
  const response = await fetch(`${API_BASE_URL}/api/quotes/${quoteId}`, {
    method: "DELETE",
  });
  if (!response.ok) throw new Error("Erreur lors de la suppression du devis");
}
