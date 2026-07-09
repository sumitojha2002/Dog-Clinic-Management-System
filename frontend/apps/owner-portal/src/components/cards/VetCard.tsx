import TwoPac from "../../assets/vet1.jpg";
import type { VetsCard } from "../../services/api/apitypes";
import { Button } from "../ui/button";

function VetCard(vetInfo: VetsCard) {
  return (
    <div className="border rounded-[30px]">
      <div>
        <img
          src={vetInfo.imageURL ?? ""}
          alt="vet-img"
          className="object-cover h-90 w-full rounded-t-[30px]"
        />
      </div>
      <div className="p-7 flex flex-col gap-3">
        <div className="flex text-[20px] gap-2 font-semibold">
          <h1>Dr. {vetInfo.name}</h1>
        </div>
        <div className="flex gap-2">
          {vetInfo.specialization && (
            <div className="flex gap-3">
              {Array.isArray(vetInfo.specialization) ?
                vetInfo.specialization.map((spec: string) => (
                  <div
                    key={spec}
                    className="border pl-2 pr-2 rounded-2xl bg-[#FFE6F2]"
                  >
                    {spec}
                  </div>
                ))
              : vetInfo.specialization}
            </div>
          )}
        </div>
        <Button className="w-full"> View profile</Button>
      </div>
    </div>
  );
}

export default VetCard;
