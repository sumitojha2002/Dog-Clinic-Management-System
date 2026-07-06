import { useForm } from "react-hook-form";
import { Button } from "../components/ui/button";
import { useEffect, useState } from "react";
import DogProfilePic from "../../src/assets/dogpfp.png";
import { ArrowLeft } from "lucide-react";
import { useNavigate } from "react-router-dom";
import { dogInfoSchema } from "../utils/helpers";
import { zodResolver } from "@hookform/resolvers/zod";
import { z } from "zod";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { postNewDogProfile } from "../services/api/authapi";
import { useAuth } from "../components/provider/AuthProvider";
import type { BackendError } from "../services/api/apitypes";

type DogInfo = z.infer<typeof dogInfoSchema>;

function DogsProfileAdd() {
  const auth = useAuth();
  const queryClient = useQueryClient();

  const {
    register,
    handleSubmit,
    watch,
    formState: { errors },
  } = useForm<DogInfo>({
    resolver: zodResolver(dogInfoSchema),
  });

  const [preview, setPreview] = useState<string | null>(null);

  const imageFile = watch("imageUrl");

  const navigate = useNavigate();

  useEffect(() => {
    if (!imageFile || imageFile.length === 0) {
      setPreview(null);
      return;
    }

    const objectUrl = URL.createObjectURL(imageFile[0]);
    setPreview(objectUrl);

    return () => URL.revokeObjectURL(objectUrl);
  }, [imageFile]);

  const mutation = useMutation({
    mutationFn: (formData: FormData) =>
      postNewDogProfile(auth.accessToken, formData),
    onSuccess: (data) => {
      alert(data.message);
      queryClient.refetchQueries({ queryKey: ["OwnersDogsCardInfo"] });
    },
    onError: (data: BackendError) => {
      alert(data.message);
    },
  });

  const submitForm = (data: DogInfo) => {
    const formData = new FormData();

    formData.append("name", data.name);
    formData.append("breed", data.breed);
    formData.append("gender", data.gender);
    formData.append("dateOfBirth", data.dateOfBirth);
    formData.append("imageUrl", data.imageUrl[0]);

    mutation.mutate(formData);
  };

  return (
    <div className="text-left mt-20">
      <div>
        <ArrowLeft
          size={25}
          className="mb-4 cursor-pointer"
          onClick={() => navigate(-1)}
        />
      </div>
      <div>
        <h1 className="text-2xl mb-10">DOG PROFILE</h1>
        <form
          onSubmit={handleSubmit(submitForm)}
          className="flex border gap-10 p-10"
        >
          <label
            htmlFor="file"
            className="border flex flex-col justify-center items-center "
          >
            <img
              src={preview || DogProfilePic}
              alt="Preview"
              className="w-48 h-48 object-cover rounded"
            />
            <input
              className="border mt-5"
              type="file"
              {...register("imageUrl")}
            />
            {errors.imageUrl && (
              <div className="text-red-500">*{errors.imageUrl.message}</div>
            )}
          </label>
          <div className="flex flex-col gap-5 border w-full p-10">
            <label htmlFor="name" className="grid grid-cols-2">
              <div>Name</div>
              <div>
                <input type="text" {...register("name")} className="border-b" />
                {errors.name && (
                  <div className="text-red-500">*{errors.name.message}</div>
                )}
              </div>
            </label>
            <label htmlFor="breed" className="grid grid-cols-2">
              <div>Breed</div>
              <div>
                <input
                  type="text"
                  {...register("breed")}
                  className="border-b"
                />
                {errors.breed && (
                  <div className="text-red-500">*{errors.breed.message}</div>
                )}
              </div>
            </label>
            <label htmlFor="gender" className="flex flex-col gap-2">
              <div>Gender</div>
              <div className="grid grid-cols-2">
                <label
                  htmlFor="Male"
                  className="flex gap-10 justify-around border"
                >
                  <div className="flex justify-center items-center">Male</div>
                  <input type="radio" value="male" {...register("gender")} />
                </label>

                <label
                  htmlFor="Female"
                  className="flex gap-10 justify-around border"
                >
                  <div className="flex justify-center items-center">Female</div>
                  <input type="radio" value="female" {...register("gender")} />
                </label>
              </div>
              {errors.gender && (
                <div className="text-red-500 text-center">
                  *{errors.gender.message}
                </div>
              )}
            </label>
            <div>
              <label htmlFor="date" className="grid grid-cols-2">
                <div>Date of Birth</div>
                <input type="date" {...register("dateOfBirth")} />
              </label>
              {errors.dateOfBirth && (
                <div className="text-red-500 text-center">
                  *{errors.dateOfBirth.message}
                </div>
              )}
            </div>
            <Button type="submit" disabled={mutation.isPending}>
              {mutation.isPending ? "Adding Dog..." : "Add Dog"}
            </Button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default DogsProfileAdd;
