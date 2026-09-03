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
          <select>
            <option value="All vets">All vets</option>
            <option value="Nirbhay Sunwar">Nirbhary Sunwar</option>
          </select>
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
          <TableBody>
            <TableRow>
              <TableCell>9:00 AM</TableCell>
              <TableCell>
                <h1 className="font-semibold">Anish Gurung</h1>
                <span className="text-gray-500">Bruno</span>
              </TableCell>
              <TableCell className="font-semibold">Dr.Sharma</TableCell>
              <TableCell className="font-semibold">Checkup</TableCell>
              <TableCell>
                <Badge className="bg-green-800 text-green-500">Completed</Badge>
              </TableCell>
              <TableCell>
                <Ellipsis />
              </TableCell>
            </TableRow>
          </TableBody>
        </Table>
      </div>
    </div>
  );
}

export default Appointment;
