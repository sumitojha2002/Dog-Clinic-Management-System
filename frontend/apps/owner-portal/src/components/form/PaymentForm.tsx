import { useState } from "react";
import {
  PaymentElement,
  useStripe,
  useElements,
} from "@stripe/react-stripe-js";
import { useNavigate } from "react-router-dom";

export function PaymentForm({ orderId }: { orderId: number }) {
  const stripe = useStripe();
  const elements = useElements();
  // const navigate = useNavigate();
  const [isProcessing, setIsProcessing] = useState(false);
  const [errorMessage, setErrorMessage] = useState<string | null>(null);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!stripe || !elements) return;

    setIsProcessing(true);
    setErrorMessage(null);

    const { error, paymentIntent } = await stripe.confirmPayment({
      elements,
      redirect: "if_required", // stay on this page instead of full redirect
    });

    if (error) {
      setErrorMessage(error.message ?? "Payment failed. Please try again.");
      setIsProcessing(false);
      return;
    }

    if (paymentIntent?.status === "succeeded") {
      // Confirm with your backend (Option B verify endpoint, or just rely on webhook)
      // await apiClient.get(`/payments/verify/${paymentIntent.id}`);
    }

    setIsProcessing(false);
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      <PaymentElement />
      {errorMessage && <p className="text-sm text-red-600">{errorMessage}</p>}
      <button
        type="submit"
        disabled={!stripe || isProcessing}
        className="w-full rounded-md bg-black py-2 text-white disabled:opacity-50"
      >
        {isProcessing ? "Processing..." : "Pay now"}
      </button>
    </form>
  );
}
