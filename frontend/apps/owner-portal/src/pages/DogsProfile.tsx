import { ArrowLeft } from "lucide-react";
import { useNavigate, useParams } from "react-router-dom";
import { Button } from "../components/ui/button";
import { useQuery } from "@tanstack/react-query";
import { getDogProfileById } from "../services/api/authapi";
import { useAuth } from "../components/provider/AuthProvider";

function DogsProfile() {
  const auth = useAuth();
  const { id } = useParams();

  const { data, error, isPending } = useQuery({
    queryKey: ["DogsProfile"],
    queryFn: () => getDogProfileById(auth.accessToken, id),
  });

  const navigate = useNavigate();
  if (isPending) {
    return <div>Loading....</div>;
  }

  if (error) {
    return <div>{error.message}</div>;
  }

  return (
    <div className="mb-10">
      <div className="mb-5">
        <ArrowLeft
          size={20}
          className="cursor-pointer"
          onClick={() => navigate(-1)}
        />
      </div>
      <div className="text-left">
        <h1 className="text-2xl">DOGS PROFILE</h1>
      </div>
      <div className="border h-auto mt-5 p-10 text-left flex flex-col gap-10">
        <div>
          <img
            src={data.data?.imageUrl ?? ""}
            alt=""
            className="h-40 border w-40 object-contain"
          />
        </div>
        <div className="grid grid-cols-2 gap-30">
          <div className="grid grid-cols-2">
            <h1>Name</h1>
            <h1 className="border-b">{data.data?.name}</h1>
          </div>
          <div className="grid grid-cols-2">
            <h1>Date of Birth</h1>
            <h1 className="border-b">{data.data?.dateOfBirth}</h1>
          </div>
        </div>
        <div className="grid grid-cols-2 gap-30">
          <div className="grid grid-cols-2">
            <h1>Gender</h1>
            <h1 className="border-b">{data.data?.gender}</h1>
          </div>
          <div className="grid grid-cols-2">
            <h1>Color</h1>
            <h1 className="border-b">{data.data?.color}</h1>
          </div>
        </div>
        <div className="grid grid-cols-2 gap-30">
          <div className="grid grid-cols-2">
            <h1>Weight</h1>
            <h1 className="border-b">
              {data.data?.weight ? data.data.weight + " Kgs" : ""}
            </h1>
          </div>
          <div className="grid grid-cols-2">
            <h1>Vactionation Status</h1>
            <h1 className="border-b">{data.data?.vactionationStatus}</h1>
          </div>
        </div>
        <div className="grid grid-cols-2 gap-30">
          <div className="grid grid-cols-2">
            <h1>Allergies</h1>
            <h1 className="border-b">
              {data.data?.allergies && data.data.allergies.length > 0 ?
                <div>
                  {data.data.allergies.map((allergies) => (
                    <div>{allergies}</div>
                  ))}
                </div>
              : <div></div>}
            </h1>
          </div>
          <div className="grid grid-cols-2">
            <h1>Chronic Conditions</h1>
            <h1 className="border-b">
              {(
                data?.data?.chronicConditions &&
                data.data.chronicConditions.length > 0
              ) ?
                <div>
                  {data.data.chronicConditions.map((chronic) => (
                    <div>{chronic}</div>
                  ))}
                </div>
              : <div></div>}
            </h1>
          </div>
        </div>
        <div className="grid grid-cols-2 gap-30">
          <div className="grid grid-cols-2">
            <h1>Registered Date</h1>
            <h1 className="border-b">{data.data?.registeredDate}</h1>
          </div>
          <div className="grid grid-cols-2">
            <h1>Last Visit Date</h1>
            <h1 className="border-b">{data.data?.lastVisistDate}</h1>
          </div>
        </div>
        <div className="grid grid-cols-2 gap-30">
          <div className="grid grid-cols-2">
            <h1>Status</h1>
            <h1 className="border-b">{data.data?.status}</h1>
          </div>
        </div>
        <div>
          <Button className="flex border w-full"> Book An Appointent</Button>
        </div>
      </div>
    </div>
  );
}

export default DogsProfile;
