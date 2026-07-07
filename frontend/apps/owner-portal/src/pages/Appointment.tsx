import { useForm } from "react-hook-form";
import { AppointmentSchema } from "../utils/helpers";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import { Button } from "../components/ui/button";
import { useMutation } from "@tanstack/react-query";
import { useAuth } from "../components/provider/AuthProvider";
import { bookAppointmentDog } from "../services/api/authapi";
import { useNavigate, useParams } from "react-router-dom";
import type { AppointmentErrors, BackendError, Response } from "../services/api/apitypes";
import type { AxiosError } from "axios";

type appointmentForm = z.infer<typeof AppointmentSchema>;

function Appointment() {
  const { id } = useParams();
  const auth = useAuth();
  const {
    register,
    handleSubmit,
    setError,
    formState: { errors, isSubmitting },
  } = useForm<appointmentForm>({
    resolver: zodResolver(AppointmentSchema),
  });

  const navigate = useNavigate();
  const mutation = useMutation({
    mutationFn: (formDataForDog: FormData) =>
      bookAppointmentDog(auth.accessToken, id, formDataForDog),
    onSuccess: (data: Response) => {
      alert(data.message);
      navigate("/dogs")
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

  const bookAppointment = (data: appointmentForm) => {
    const formData = new FormData();
    formData.append("reason", data.reason);
    formData.append("appointmentDate", data.appointmentDate);
    formData.append("appointmentTime", data.appointmentTime);
    formData.append("vetId", data.vetId.toString());
    mutation.mutate(formData);
  };

  return (
    <div className="text-left">
      <div>
        <h1 className="text-2xl mt-10">Book appointment</h1>
      </div>
      <div>
        <div className="mt-10 flex justify-center">
          <form
            onSubmit={handleSubmit(bookAppointment)}
            className="w-1/2 flex gap-10 p-20 flex-col justify-center items-center border"
          >
            <label
              htmlFor="appointmentdate"
              className="flex w-full justify-between"
            >
              <div>Set Appointment Date</div>
              <div className="flex flex-col w-1/2 text-center">
                <input type="date" {...register("appointmentDate")} className="border-b"/>
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
              <div>Set Appointment Date</div>
              <div className="w-1/2  justify-center flex flex-col">
                <input type="time" {...register("appointmentTime")} className="border-b"/>
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
              className="flex w-full justify-baseline"
              htmlFor="veterninarianId"
            >
              <div className="w-1/2">Veteriniarian id</div>
              <div className="w-1/2 flex flex-col justify-center ">
                <input type="number" {...register("vetId")} className="border-b"/>
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
            <Button
              className="w-full"
              disabled={mutation.isPending || isSubmitting}
            >
              {mutation.isPending ? "Booking Appointment" : "Book Appointment"}
            </Button>
          </form>
        </div>
      </div>
    </div>
  );
}

export default Appointment;
