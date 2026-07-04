import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { useAuth } from "../components/provider/AuthProvider";
import { getOwnerProfile, updateOwnerProfile } from "../services/api/authapi";
import { Button } from "../components/ui/button";
import { useEffect, useState, type FormEvent } from "react";
import type { UserUpdateProfileInfo } from "../services/api/apitypes";

function Profile() {
  const auth = useAuth();
  const [edit, setEdit] = useState<boolean>(false);
  const [phoneNumber, setPhoneNumber] = useState<string>("");
  const [address, setAddress] = useState<string>("");
  const queryClinet = useQueryClient();

  const { data, error, isPending } = useQuery({
    queryKey: ["ownerProfile"],
    queryFn: () => getOwnerProfile(auth.accessToken),
    staleTime: Infinity,
    refetchOnMount: false,
    refetchOnWindowFocus: false,
    refetchOnReconnect: false,
  });

  useEffect(() => {
    if (data?.data) {
      setPhoneNumber(data.data.phoneNumber ?? "");
      setAddress(data.data.address ?? "");
    }
  }, [data]);

  const isUnchanged =
    phoneNumber === (data?.data?.phoneNumber ?? "") &&
    address === (data?.data?.address ?? "");

  const handleSubmit = (e: FormEvent) => {
    e.preventDefault();

    if (isUnchanged) return;
    mutation.mutate({
      phoneNumber,
      address,
    });
  };

  const mutation = useMutation({
    mutationFn: (data: UserUpdateProfileInfo) =>
      updateOwnerProfile(auth.accessToken, data),
    onSuccess: (response) => {
      queryClinet.setQueryData(["ownerProfile"], (old: typeof data) => {
        if (!old) return old;
        return {
          ...old,
          data: {
            ...old.data,
            ...response.data,
          },
        };
      });
      alert(response.message);
      setEdit(false);
    },
  });
  if (isPending) return <div>Loading...</div>;

  return (
    <div className="mt-10 text-start">
      <div className="flex justify-between">
        <h1 className="text-2xl ">OWNER PROFILE</h1>
      </div>
      <div className="flex flex-col justify-center items-center">
        <form
          className="border p-10 mt-10 text-left w-1/2 grid gap-5"
          onSubmit={handleSubmit}
        >
          <div className="grid grid-cols-2 ">
            <label>Username</label>
            <input
              type="text"
              className="border"
              value={data?.data?.user.username}
              disabled
            />
          </div>
          <div className="grid grid-cols-2">
            <label>Email</label>
            <input
              type="text"
              disabled
              className="border"
              value={data?.data?.user.email}
            />
          </div>
          <div className="grid grid-cols-2">
            <label>Address</label>
            <input
              type="text"
              disabled={!edit}
              className="border"
              placeholder="Enter your address"
              value={address ? address : ""}
              onChange={(e) => setAddress(e.target.value)}
            />
          </div>
          <div className="grid grid-cols-2">
            <label>Phone number</label>
            <input
              type="tel"
              disabled={!edit}
              className="border"
              placeholder="Enter your phone number"
              value={phoneNumber ? phoneNumber : ""}
              onChange={(e) => setPhoneNumber(e.target.value)}
            />
          </div>
          <div className="grid grid-cols-2">
            <label>Registration date</label>
            <input
              type="text"
              disabled
              className="border"
              value={data?.data?.registrationDate}
            />
          </div>
          {edit && (
            <div className="mt-10">
              <Button
                className="flex w-full"
                type="submit"
                disabled={isUnchanged}
              >
                Save
              </Button>
            </div>
          )}
        </form>
        <div className="w-full mt-2 pl-20 pr-20 flex justify-center">
          <Button
            variant={edit ? "destructive" : "default"}
            className="w-1/2"
            onClick={() => setEdit(!edit)}
          >
            {edit ? "Cancel" : "Edit"}
          </Button>
        </div>
      </div>
    </div>
  );
}

export default Profile;
