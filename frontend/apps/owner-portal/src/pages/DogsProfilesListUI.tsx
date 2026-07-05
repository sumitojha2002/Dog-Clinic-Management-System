import { useQuery } from "@tanstack/react-query";
import DogsProfileCard from "../components/cards/DogsProfileCard";
import { getAllDogsCardInfo } from "../services/api/authapi";
import { useAuth } from "../components/provider/AuthProvider";

function DogsProfilesListUI() {
  const auth = useAuth();
  const { data, error, isPending } = useQuery({
    queryKey: ["OwnersDogsCardInfo"],
    queryFn: () => getAllDogsCardInfo(auth.accessToken),
    staleTime: Infinity,
    refetchOnMount: false,
    refetchOnReconnect: false,
    refetchOnWindowFocus: false,
  });

  if (isPending) {
    return <div>loading...</div>;
  }

  if (error) return <div>Something went wrong: {error.message}</div>;

  return (
    <div className="text-left">
      <div>
        <h1 className="text-2xl">DOGS PROFILES</h1>
      </div>
      {data?.data && data.data.length > 0 ?
        <div className="grid grid-cols-3 gap-10 mt-10">
          {data.data.map((dogsdata) => (
            <DogsProfileCard key={dogsdata.id} {...dogsdata} />
          ))}
        </div>
      : <p>No dogs found.</p>}
    </div>
  );
}

export default DogsProfilesListUI;
