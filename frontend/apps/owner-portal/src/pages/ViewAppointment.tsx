import { useQuery } from "@tanstack/react-query";
import { useNavigate, useParams } from "react-router-dom";
import { getAllAppointments } from "../services/api/authapi";
import { useAuth } from "../components/provider/AuthProvider";
import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "../components/ui/table";
import { ArrowLeft } from "lucide-react";
import { tagsBasedColor } from "../utils/helpers";

function ViewAppointment() {
  const { id } = useParams();
  const auth = useAuth();
  const navigate = useNavigate();

  const { data, isError, isPending } = useQuery({
    queryKey: ["dogsAppotmentList", id],
    queryFn: () => getAllAppointments(auth.accessToken, id),
  });

  if (isPending) return <div>Loading...</div>;
  if (isError) return <div>"Error happen while fetching data."</div>;
  return (
    <div className="mt-20">
      <div>
        <div className="mb-10">
          <ArrowLeft size={20} onClick={() => navigate(-1)} />
        </div>
        <h1 className="text-start text-3xl font-semibold mb-10">
          Appointments
        </h1>
      </div>
      <div>
        <Table>
          {data?.data?.length === 0 && (
            <TableCaption>List of all the appointments</TableCaption>
          )}
          <TableHeader>
            <TableRow>
              <TableHead className="text-center">AppointemntId</TableHead>
              <TableHead className="text-center">Dog name</TableHead>
              <TableHead className="text-center">Reason</TableHead>
              <TableHead className="text-center">Appointment Status</TableHead>
              <TableHead className="text-center">Vet name</TableHead>
              <TableHead className="text-center">Appointment Date</TableHead>
              <TableHead className="text-center">Appointment Time</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {data?.data &&
              data?.data.map((dog) => (
                <TableRow>
                  <TableCell>{dog.appId}</TableCell>
                  <TableCell>{dog.dogs.name}</TableCell>
                  <TableCell>{dog.reason}</TableCell>
                  <TableCell className={tagsBasedColor(dog.appointmentStatus)}>
                    {dog.appointmentStatus}
                  </TableCell>
                  <TableCell>{dog.vetInfo.name}</TableCell>
                  <TableCell>{dog.appointmentDate}</TableCell>
                  <TableCell>{dog.appLocalTime}</TableCell>
                </TableRow>
              ))}
          </TableBody>
        </Table>
        {data?.data?.length === 0 && (
          <div>
            <h1 className="text-2xl text-center mt-10">
              No appointments made yet.
            </h1>
          </div>
        )}
      </div>
    </div>
  );
}

export default ViewAppointment;
