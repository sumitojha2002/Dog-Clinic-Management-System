import VetCard from "../components/cards/VetCard";
import { useQuery } from "@tanstack/react-query";
import { getAllVets } from "../services/api/authapi";

export default function Veterinarian() {
  const timestamp = Date.now();
  const date = new Date(timestamp);
  const { data: vetInfo } = useQuery({
    queryKey: ["vetsInfo"],
    queryFn: getAllVets,
    // 1. Keep data fresh forever so it NEVER automatically refetches
    staleTime: Infinity,

    // 2. Keep the data in the cache forever (do not garbage collect it)
    gcTime: Infinity,

    // 3. Optional: Disable specific triggers for extra safety
    refetchOnWindowFocus: false, // Don't refetch when clicking back onto the browser tab
    refetchOnMount: false, // Don't refetch when the component loads again
    refetchOnReconnect: false, // Don't refetch if network goes down and comes back
  });
  const formatted = new Intl.DateTimeFormat("en-US", {
    month: "short",
    day: "numeric",
  }).format(date);
  return (
    <div className="p-5 pl-15 pr-10">
      <div className="text-5xl">Veterinarian</div>
      <span className="text-[15px] text-gray-600 ">
        Front desk · today, {formatted}
      </span>
      {vetInfo?.data?.length == 0 ?
        <div className="mt-10 text-center">
          <h1 className="text-2xl">No Vets Found</h1>
        </div>
      : <div className="mt-10 grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 sm:grid-cols-1 gap-6">
          {vetInfo?.data?.map((vetinfo, index) => (
            <VetCard
              name={vetinfo.name}
              specility={vetinfo.specialization}
              image={vetinfo.imageURL}
            />
          ))}
        </div>
      }
    </div>
  );
}
