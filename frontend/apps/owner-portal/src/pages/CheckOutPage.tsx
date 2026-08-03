import { stripePromise } from "../lib/stripe";
import { Elements } from "@stripe/react-stripe-js";
import { PaymentForm } from "../components/form/PaymentForm";
import { useMutation } from "@tanstack/react-query";
import { createPaymentInstent } from "../services/api/authapi";
import { useAuth } from "../components/provider/AuthProvider";
import { useEffect } from "react";
import { useParams } from "react-router-dom";

function CheckOutPage() {
  const auth = useAuth();
  const { id } = useParams();
  const numericId = +id;
  const {
    mutate: createIntent,
    data,
    isPending,
    isError,
  } = useMutation({
    mutationKey: ["payment-intent"],
    mutationFn: () => createPaymentInstent(auth.accessToken, numericId),
  });

  useEffect(() => {
    createIntent();
  }, [id]);

  if (isPending || !data) return <div>Preparing checkout...</div>;
  if (isError) return <div>Couldn't start checkout. Please try again.</div>;

  const clientSecret = (data.data as { clientSecret?: string } | undefined)
    ?.clientSecret;

  return (
    <Elements
      stripe={stripePromise}
      options={{
        clientSecret,
        appearance: { theme: "stripe" },
      }}
    >
      <PaymentForm orderId={id} />
    </Elements>
  );
}

export default CheckOutPage;
