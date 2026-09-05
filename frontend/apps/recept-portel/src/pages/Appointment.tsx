import { Ellipsis, Funnel } from "lucide-react";
import React from "react";
import { Button } from "../components/ui/button";
import { Input } from "../components/ui/input";
import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "../components/ui/table";
import { Badge } from "../components/ui/badge";
import { useQuery } from "@tanstack/react-query";
import { getAllAppointments, getAllVetsList } from "../services/api/authapi";
import { useAuth } from "../components/providers/AuthProvider";
import { respectiveColor } from "../utils/formatter";

function Appointment() {
  const timestamp = Date.now();
  const date = new Date(timestamp);

  const formatted = new Intl.DateTimeFormat("en-US", {
    month: "short",
    day: "numeric",
  }).format(date);
  const list = [
    {
      id: 1,
      name: "Today's Total",
      value: 18,
    },
    {
      id: 2,
      name: "Waiting",
      value: 4,
    },
    {
      id: 3,
      name: "Completed",
      value: 2,
    },
    {
      id: 4,
      name: "Remaining",
      value: 5,
    },
    {
      id: 5,
      name: "No show",
      value: 0,
    },
  ];

  const { data: vetList } = useQuery({
    queryKey: ["vetListData"],
    queryFn: () => getAllVetsList(accessToken),
  });

  const { accessToken } = useAuth();

  const {
    data: appointment,
    isLoading,
    error,
  } = useQuery({
    queryKey: ["appointments"],
    queryFn: () => getAllAppointments(accessToken),
  });

  console.log("appointment:", appointment);
  console.log("appointment.data:", appointment?.data);
  console.log("loading:", isLoading);
  console.log("error:", error);

  return (
    <div className="p-5 pl-15 pr-10">
      <div className=" flex justify-between">
        <div className="flex flex-col">
          <div className="text-5xl">Appointment</div>
          <span className="text-[15px] text-gray-600 ">
            Front desk · today, {formatted}
          </span>
        </div>
        <div className="flex gap-2">
          <Button className="cursor-pointer">
            <Funnel />
            Filter
          </Button>
          <Button className="cursor-pointer" variant={"outline"}>
            + New appointment
          </Button>
        </div>
      </div>
      <div className="grid grid-cols-5 gap-10 mt-10  pl-10 pr-10">
        {list.map((value) => (
          <div className="border p-10  rounded-2xl" key={value.id}>
            <h1 className="text-2xl text-center">{value.name}</h1>
            <h1 className="font-bold text-center mt-5 text-5xl">
              {value.value}
            </h1>
          </div>
        ))}
      </div>
      <div className=" grid grid-cols-2 pl-5 pr-10 gap-10 mt-15">
        <Input placeholder="Search by owner, dog, or vet" className="" />
        <div className="grid grid-cols-2 gap-10 ">
          <div className="w-full">
            {vetList?.data && vetList.data.length ?
              <select className="w-full">
                <option value="all info">All vets</option>
                {vetList.data &&
                  vetList.data.map((vets, index) => (
                    <option value={vets.name} key={index}>
                      {vets.name}
                    </option>
                  ))}
              </select>
            : <select>
                <option value="" disabled>
                  No service available
                </option>
              </select>
            }
          </div>
          <select>
            <option value="All status">All status</option>
            <option value="Nirbhay Sunwar">Nirbhary Sunwar</option>
          </select>
        </div>
      </div>
      <div className="mt-10">
        <Table>
          <TableCaption>A list of apponitments</TableCaption>
          <TableHeader>
            <TableRow>
              <TableHead>Time</TableHead>
              <TableHead>Owner/dog</TableHead>
              <TableHead>Vet</TableHead>
              <TableHead>Reason</TableHead>
              <TableHead>Status</TableHead>
              <TableHead></TableHead>
            </TableRow>
          </TableHeader>
          {appointment?.data && appointment?.data?.length > 0 ?
            <TableBody>
              {appointment?.data &&
                appointment.data.map((app, index) => (
                  <TableRow key={index}>
                    <TableCell>{app.appLocalTime}</TableCell>
                    <TableCell>
                      <h1 className="font-semibold">
                        {app.ownersProfile.user.username}
                      </h1>
                      <span className="text-gray-500">{app.dogs.name}</span>
                    </TableCell>
                    <TableCell className="font-semibold">
                      Dr. {app.vets.name}
                    </TableCell>
                    <TableCell className="font-semibold">
                      {app.reason}
                    </TableCell>
                    <TableCell>
                      <Badge className="bg-green-800 text-green-500">
                        {respectiveColor(app.appointmentStatus)?.name}
                      </Badge>
                    </TableCell>
                    <TableCell>
                      <Ellipsis />
                    </TableCell>
                  </TableRow>
                ))}
            </TableBody>
          : <div></div>}
        </Table>
        <div>
          {appointment?.data?.length == 0 && <div>{appointment?.status}</div>}
        </div>
      </div>
    </div>
  );
}

export default Appointment;
