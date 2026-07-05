import { useQuery } from "@tanstack/react-query";
import DogsProfileCard from "../components/cards/DogsProfileCard";
import { getAllDogsCardInfo } from "../services/api/authapi";
import { useAuth } from "../components/provider/AuthProvider";
import { Button } from "../components/ui/button";
import { useNavigate, useNavigation } from "react-router-dom";

function DogsProfilesListUI() {
  const auth = useAuth();
  const navigation = useNavigate();
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
      <div className="flex justify-between">
        <h1 className="text-2xl">DOGS PROFILES</h1>
        <Button className="pl-3 pr-3" onClick={() => navigation("/dogs")}>
          Add Pet
        </Button>
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
