import { ArrowLeft } from "lucide-react";
import { useNavigate, useParams } from "react-router-dom";
import { Button } from "../components/ui/button";
import { useMutation, useQuery } from "@tanstack/react-query";
import { getDogProfileById, updateDogsProfile } from "../services/api/authapi";
import { useAuth } from "../components/provider/AuthProvider";
import DogDefaultProfile from "../../src/assets/dogpfp.png";
import { useForm } from "react-hook-form";

import { useEffect, useState } from "react";
import { zodResolver } from "@hookform/resolvers/zod";
import z from "zod";
import { dogProfileUpdate } from "../utils/helpers";

type PetsInfoForUpdate = z.infer<typeof dogProfileUpdate>;

function DogsProfile() {
  const auth = useAuth();
  const { id } = useParams();
  const [edit, setEdit] = useState<boolean>(false);
  const { data, error, isPending } = useQuery({
    queryKey: ["DogsProfile", id],
    queryFn: () => getDogProfileById(auth.accessToken, id),
    enabled: !!id,
  });

  const genderValue: "male" | "female" =
    data?.data?.gender === "female" ? "female" : "male";

  const {
    register,
    handleSubmit,
    watch,
    formState: { dirtyFields, errors, isDirty },
  } = useForm<PetsInfoForUpdate>({
    resolver: zodResolver(dogProfileUpdate),
    values: {
      name: data?.data?.name ?? "",
      gender: genderValue,
      breed: data?.data?.breed ?? "",
      color: data?.data?.color ?? "",
      dateOfBirth: data?.data?.dateOfBirth ?? "",
    },
  });

  const [selectedFileUrl, setSelectedFileUrl] = useState<string | null>(null);
  const pfpFile = watch("imageUrl");

  const navigate = useNavigate();

  useEffect(() => {
    if (pfpFile instanceof FileList && pfpFile.length > 0) {
      const objectUrl = URL.createObjectURL(pfpFile[0]);
      setSelectedFileUrl(objectUrl);
      return () => URL.revokeObjectURL(objectUrl);
    }
  }, [pfpFile]);

  const displayImage =
    selectedFileUrl ?? data?.data?.imageUrl ?? DogDefaultProfile;

  const mutation = useMutation({
    mutationFn: (formData: FormData) =>
      updateDogsProfile(auth.accessToken, id, formData),
  });
  const updateProfile = (data: PetsInfoForUpdate) => {
    console.log("has been used");
    const formdata = new FormData();
    if (dirtyFields.name && data?.name) formdata.append("name", data.name);
    if (dirtyFields.breed && data?.breed) formdata.append("breed", data.breed);
    if (dirtyFields.color && data?.color) formdata.append("color", data.color);
    if (dirtyFields.dateOfBirth && data?.dateOfBirth)
      formdata.append("dateOfBirth", data.dateOfBirth);
    if (dirtyFields.imageUrl && data?.imageUrl?.[0])
      formdata.append("imageUrl", data.imageUrl[0]);
    if (dirtyFields.gender && data?.gender)
      formdata.append("gender", data.gender);

    for (let [key, value] of formdata.entries()) {
      console.log(`${key}:`, value);
    }
    console.log(dirtyFields);
    mutation.mutate(formdata);
  };

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
        <form
          onSubmit={handleSubmit(updateProfile)}
          className="flex flex-col gap-10"
        >
          <div className="flex justify-between">
            <div className="flex flex-col gap-3">
              <img
                src={displayImage}
                alt=""
                className="h-40 border w-40 object-contain"
              />
              <div className="flex flex-col gap-3">
                <input type="file" {...register("imageUrl")} />
              </div>
            </div>
            <div>
              <Button type="button" onClick={() => setEdit(!edit)}>
                {edit ? "Editing" : "Edit"}
              </Button>
            </div>
          </div>
          <div className="grid grid-cols-2 gap-30">
            <div className="grid grid-cols-2">
              <h1>Name</h1>
              <div className="flex flex-col gap-2">
                {edit ?
                  <input
                    type="text"
                    {...register("name")}
                    className="border-b"
                  />
                : <p className="text-[#a0a0a0]">{data?.data?.name}</p>}
                {edit && errors.name && (
                  <span className="text-red-500">*{errors.name.message}</span>
                )}
              </div>
            </div>
            <div className="grid grid-cols-2">
              <h1>Date of Birth</h1>
              <div className="flex flex-col gap-2">
                {edit ?
                  <input type="date" {...register("dateOfBirth")} />
                : <p className="text-[#a0a0a0]">{data?.data?.dateOfBirth}</p>}
                {edit && errors.dateOfBirth && (
                  <span className="text-red-500">
                    *{errors.dateOfBirth.message}
                  </span>
                )}
              </div>
            </div>
          </div>
          <div className="grid grid-cols-2 gap-30">
            <div className="grid grid-cols-2">
              <h1>Gender</h1>
              <div>
                {edit ?
                  <div className="flex justify-between">
                    <label className="flex gap-2">
                      <h1>male</h1>
                      <input
                        type="radio"
                        {...register("gender")}
                        value="male"
                      />
                    </label>
                    <label className="flex gap-2">
                      <h1>female</h1>
                      <input
                        type="radio"
                        {...register("gender")}
                        value="female"
                      />
                    </label>
                  </div>
                : <p className="text-[#a0a0a0]">{data.data?.gender}</p>}
              </div>
            </div>
            <div className="grid grid-cols-2">
              <h1>Color</h1>
              <div className="flex flex-col gap-2">
                {edit ?
                  <input type="text" {...register("color")} />
                : <p className="text-[#a0a0a0]">{data?.data?.color}</p>}
                {edit && errors.color && (
                  <p className="text-red-500">* {errors.color.message}</p>
                )}
              </div>
            </div>
          </div>
          <div className="grid grid-cols-2 gap-30">
            <div className="grid grid-cols-2">
              <h1>Weight</h1>
              <h1 className="border-b text-[#a0a0a0] flex flex-col gap-3">
                <div>{data.data?.weight ? data.data.weight + " Kgs" : ""}</div>
                <div></div>
              </h1>
            </div>
            <div className="grid grid-cols-2">
              <h1>Breed</h1>
              <div className="flex flex-col gap-3">
                {edit ?
                  <input
                    type="text"
                    className="border-b"
                    {...register("breed")}
                  />
                : <p className="text-[#a0a0a0]">{data?.data?.breed}</p>}
                {edit && errors.breed && (
                  <p className="text-red-500">*{errors.breed.message}</p>
                )}
              </div>
            </div>
          </div>
        </form>

        <div className="grid grid-cols-2 gap-30">
          <div className="grid grid-cols-2">
            <h1>Allergies</h1>
            <h1 className="border-b">
              {data.data?.allergies && data.data.allergies.length > 0 ?
                <div>
                  {data.data.allergies.map((allergies) => (
                    <div className="text-[#a0a0a0]">{allergies}</div>
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
                    <div className="text-[#a0a0a0]">{chronic}</div>
                  ))}
                </div>
              : <div></div>}
            </h1>
          </div>
        </div>
        <div className="grid grid-cols-2 gap-30">
          <div className="grid grid-cols-2">
            <h1>Registered Date</h1>
            <h1 className="border-b text-[#a0a0a0]">
              {data.data?.registeredDate}
            </h1>
          </div>
          <div className="grid grid-cols-2">
            <h1>Last Visit Date</h1>
            <h1 className="border-b text-[#a0a0a0]">
              {data.data?.lastVisistDate}
            </h1>
          </div>
        </div>
        <div className="grid grid-cols-2 gap-30">
          <div className="grid grid-cols-2">
            <h1>Status</h1>
            <h1 className="border-b text-[#a0a0a0]">{data.data?.status}</h1>
          </div>
          <div>
            <h1></h1>
          </div>
        </div>
        <div>
          {edit && (
            <Button
              onClick={handleSubmit(updateProfile)}
              className="flex w-full"
              disabled={mutation.isPending || !isDirty}
            >
              {mutation.isPending ? "Saving changes..." : "Save changes"}
            </Button>
          )}
        </div>
        <div>
          <Button
            disabled={edit}
            className="flex border w-full"
            onClick={() => navigate(`/dogs/${id}/appointment/new`)}
          >
            Book An Appointent
          </Button>
        </div>
      </div>
    </div>
  );
}

export default DogsProfile;
