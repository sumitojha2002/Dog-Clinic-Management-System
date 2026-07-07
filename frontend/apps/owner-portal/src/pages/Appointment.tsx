import { useForm } from "react-hook-form";
import { AppointmentSchema } from "../utils/helpers";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import { Button } from "../components/ui/button";
import { useMutation, useQuery } from "@tanstack/react-query";
import { useAuth } from "../components/provider/AuthProvider";
import {
  bookAppointmentDog,
  getAllVet,
  getVetTimeings,
} from "../services/api/authapi";
import { useNavigate, useParams } from "react-router-dom";
import type {
  AppointmentErrors,
  BackendError,
  Response,
} from "../services/api/apitypes";
import type { AxiosError } from "axios";
import { ArrowLeft } from "lucide-react";

type appointmentForm = z.infer<typeof AppointmentSchema>;

function Appointment() {
  const { id } = useParams();
  const auth = useAuth();
  const {
    register,
    handleSubmit,
    setError,
    watch,
    formState: { errors, isSubmitting },
  } = useForm<appointmentForm>({
    resolver: zodResolver(AppointmentSchema),
  });

  const watchVetId = watch("vetId");
  const watchDate = watch("appointmentDate");

  const navigate = useNavigate();
  const { mutate: bookApp, isPending: isAppointing } = useMutation({
    mutationFn: (formDataForDog: FormData) =>
      bookAppointmentDog(auth.accessToken, id, formDataForDog),
    onSuccess: (data: Response) => {
      alert(data.message);
      navigate("/dogs/profile");
    },
    onError: (error: AxiosError<BackendError<AppointmentErrors>>) => {
      const errorsfrombackend = error.response.data.errors;
      if (error.response.data.message) {
        alert(error.response.data.message);
      }
      if (errorsfrombackend.reason) {
        setError("reason", { message: errorsfrombackend.reason });
      }

      if (errorsfrombackend.appointmentDate) {
        setError("appointmentDate", {
          message: errorsfrombackend.appointmentDate,
        });
      }
      if (errorsfrombackend.appointmentTime) {
        setError("appointmentTime", {
          message: errorsfrombackend.appointmentTime,
        });
      }
      if (errorsfrombackend.vetId) {
        setError("vetId", { message: errorsfrombackend.vetId });
      }
    },
  });

  const {
    data: opts,
    isError: isOptsError,
    isLoading: isOptsIsLoading,
  } = useQuery({
    queryKey: ["vetList"],
    queryFn: () => getAllVet(auth.accessToken),
    staleTime: Infinity,
    refetchOnMount: false,
    refetchOnWindowFocus: false,
    refetchOnReconnect: false,
  });

  const {
    data: vetTiming,
    isError: isVetTimingIsError,
    isLoading: isVetTimingIsLoading,
  } = useQuery({
    queryKey: ["vetTiming", watchVetId, watchDate],
    queryFn: () => getVetTimeings(auth.accessToken, watchVetId, watchDate),
    enabled: !!watchDate && !!watchVetId && watchVetId !== "",
    staleTime: 1000 * 60 * 5,
  });

  const bookAppointment = (data: appointmentForm) => {
    const formData = new FormData();
    formData.append("reason", data.reason);
    formData.append("appointmentDate", data.appointmentDate);
    formData.append("appointmentTime", data.appointmentTime);
    formData.append("vetId", data.vetId.toString());
    bookApp(formData);
  };

  return (
    <div className="text-left mt-10">
      <div>
        <ArrowLeft size={20} onClick={() => navigate(-1)} />
      </div>
      <div>
        <h1 className="text-2xl mt-10">Book appointment</h1>
      </div>
      <div>
        <div className="mt-10 flex justify-center">
          <form
            onSubmit={handleSubmit(bookAppointment)}
            className="w-1/2 flex gap-10 p-20 flex-col justify-center items-center border"
          >
            {/* veterinarian */}
            <label
              className="flex w-full justify-baseline"
              htmlFor="veterninarianId"
            >
              <div className="w-1/2">Veterinarian </div>
              <div className="w-1/2 flex flex-col justify-center ">
                <select
                  name="vet"
                  id="vet"
                  {...register("vetId")}
                  disabled={isOptsIsLoading || isOptsError}
                >
                  {isOptsIsLoading && <option value="">Loading...</option>}
                  {isOptsError && (
                    <option value="">Error fetching data. Try again.</option>
                  )}

                  {opts &&
                    opts.data.map((opt) => (
                      <option key={opt.vetId} value={opt.vetId}>
                        {opt.userinfo.username}
                      </option>
                    ))}
                </select>
                {errors.vetId ? (
                  <div className="text-red-500 text-center">
                    *{errors.vetId.message}
                  </div>
                ) : (
                  <></>
                )}
              </div>
            </label>
            <label
              htmlFor="appointmentdate"
              className="flex w-full justify-between"
            >
              <div>Set Appointment Date</div>
              <div className="flex flex-col w-1/2 text-center">
                <input
                  type="date"
                  {...register("appointmentDate")}
                  className="border-b"
                />
                {errors.appointmentDate ? (
                  <div className="text-red-500">
                    * {errors.appointmentDate.message}
                  </div>
                ) : (
                  <></>
                )}
              </div>
            </label>

            <label
              htmlFor="appointmentTime"
              className="w-full flex justify-between"
            >
              <div>Set Appointment Time</div>
              <div className="w-1/2  justify-center flex flex-col">
                <select
                  {...register("appointmentTime")}
                  className="border-b"
                  disabled={isVetTimingIsLoading || isVetTimingIsError}
                >
                  {isVetTimingIsLoading && <option>Loading...</option>}
                  {isVetTimingIsError && <option>Error fetching timing</option>}
                  {vetTiming &&
                    vetTiming.data.map((time) => (
                      <option key={time} value={time}>
                        {time}
                      </option>
                    ))}
                </select>
                {errors.appointmentTime ? (
                  <div className="text-center text-red-500">
                    * {errors.appointmentTime.message}
                  </div>
                ) : (
                  <></>
                )}
              </div>
            </label>

            <label
              htmlFor="reason"
              className="text-center flex w-full justify-between"
            >
              <h1>Reason</h1>
              <div>
                <textarea {...register("reason")} className="border" />
                {errors.reason ? (
                  <div className="text-red-500 text-center">
                    * {errors.reason.message}
                  </div>
                ) : (
                  <></>
                )}
              </div>
            </label>
            <Button className="w-full" disabled={isAppointing || isSubmitting}>
              {isAppointing ? "Booking Appointment" : "Book Appointment"}
            </Button>
          </form>
        </div>
      </div>
    </div>
  );
}

export default Appointment;
