import React from "react";
import profileImage from "../../assets/hero.png";
import { Button } from "../ui/button";

interface VetCardProps {
  name: string;
  image: string;
  specility: string[];
}

function VetCard({ name, specility, image }: VetCardProps) {
  return (
    <div className="">
      <div className="flex flex-col gap-3 border">
        <img
          src={image}
          alt=""
          className="border self-center mt-4 w-60 h-60 object-cover"
        />

        <div className="p-2 flex flex-col gap-4">
          <h1 className="text-2xl">Dr. {name}</h1>
          <div className="flex gap-2 ">
            {specility.length == 0 ?
              <></>
            : <div className="flex gap-2">
                {specility.map((spec, index) => (
                  <div className="border rounded-2xl pl-2 pr-2" key={index}>
                    {spec}
                  </div>
                ))}
              </div>
            }
          </div>
          <Button className="w-full">View Profile</Button>
        </div>
      </div>
    </div>
  );
}

export default VetCard;
