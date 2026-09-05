import React from "react";
import { Button } from "../components/ui/button";
import { Ghost, UserPlus } from "lucide-react";
import { Input } from "../components/ui/input";
import { Badge } from "../components/ui/badge";
import { useQuery } from "@tanstack/react-query";
import { getAllAppointments } from "../services/api/authapi";

function Dashboard() {
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

  const vetstoday = [
    {
      id: 1,
      name: "Dr.Rana",
      freetime: "11:00",
    },
    {
      id: 2,
      name: "Dr.Basnet",
      freetime: "12:30",
    },
  ];

  const appointmentlist = [
    {
      id: 1,
      time: "9:00",
      name: "Bruno · owner Maya R.",
      checkedin: "Checked-in",
    },
    {
      id: 2,
      time: "9:30",
      name: "Coco · owner S. Thapa",
      checkedin: "With vet",
    },
    {
      id: 3,
      time: "10:00",
      name: "Rex · owner P. Karki",
      checkedin: "Scheduled",
    },
  ];



  return (
    <div className="p-5 pl-15 pr-10">
      <div className="flex flex-col">
        <div className="text-5xl">Dashboard</div>
        <span className="text-[15px] text-gray-600 ">
          Front desk · today, {formatted}
        </span>
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
      <div className="grid grid-cols-3 gap-2 mt-15">
        <Button variant="outline">+ New Appointment</Button>
        <Button variant="outline">
          <UserPlus /> Register walk-in
        </Button>
        <Input placeholder="Search Owner" />
      </div>
      <div className="mt-10 grid grid-cols-2 gap-5">
        <div className=" p-5">
          <h1>Today's appointments</h1>
          <div className="mt-2">
            {appointmentlist.map((value) => (
              <div className="grid grid-cols-3 border p-2" key={value.id}>
                <div>{value.time}</div>
                <h1 className="font-bold ">{value.name}</h1>
                <div className="flex justify-end">
                  <Badge>{value.checkedin}</Badge>
                </div>
              </div>
            ))}
          </div>
        </div>
        <div className=" p-5">
          <h1>Vet's Today</h1>
          <div className="mt-2">
            {vetstoday.map((value) => (
              <div className="grid grid-cols-2 border p-2" key={value.id}>
                <h1 className="font-bold ">{value.name}</h1>
                <div className="flex justify-between">
                  <h1>Next free</h1>
                  <h1>{value.freetime}</h1>
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}

export default Dashboard;
