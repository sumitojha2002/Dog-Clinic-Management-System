import { Button } from "../components/ui/button";
import vetone from "../assets/vet1-removebg-preview.png";
import vettwo from "../assets/vet2-removebg-preview.png";
import { FlaskConical } from "lucide-react";
function Home() {
  return (
    <div className=" mt-3">
      <div>
        <div className="w-full h-120.75 rounded-[45px] bg-[#D0A7DE] flex">
          <div className="w-1/2 flex flex-col justify-center text-left gap-10 pl-9">
            <div className="text-5xl flex flex-col gap-2">
              <span>Trustworthy vets</span>
              <span>putting your</span>
              <span>dogs first</span>
            </div>
            <div>
              lorem ipsum sit amet consectetur adipiselit sed do tempor
              incididunt ut labore et dolore mangna alque ut enim.
            </div>
            <div className="flex justify-start gap-3">
              <Button>Back an appointment</Button>
              <Button>Bowers all services</Button>
            </div>
          </div>
          <div className="w-1/2 flex items-end justify-end pr-20">
            <img height={500} width={400} src={vetone} alt="image" />
          </div>
        </div>
        <div className="h-90 mt-4 flex flex-col">
          <div className="flex justify-between w-full h-12">
            <div className="text-start">
              <h1 className="text-3xl ml-5 font-semibold">Our Service</h1>
            </div>
            <div className="flex gap-3">
              <Button>Back an appointment</Button>
              <Button>Bowers all services</Button>
            </div>
          </div>
          <div className="h-full flex gap-5">
            <div className="w-1/2 h-full rounded-[35px] bg-[#FFE37E] flex justify-center ">
              <img src={vettwo} alt="vet2" />
            </div>
            <div className="h-full w-1/2 flex flex-col gap-3">
              <div className="h-1/2 flex gap-4">
                <div className="w-1/2 p-4 border rounded-[35px] bg-[#D4D1D1]">
                  <div className="flex w-full justify-between">
                    <div>
                      <FlaskConical size={20} />
                    </div>
                    <div className="text-[10px] rounded-[10px] border border-black pl-1 pr-1">
                      Rs. 200
                    </div>
                  </div>
                  <div className="text-left">
                    <h1 className="font-semibold text-[15px] mb-2 mt-2">
                      In-house laboratory
                    </h1>
                    <p className="text-[11px] leading-tight">
                      Duis irure dolor inveniam nostrud reprehenderit
                      involuptatae
                    </p>
                  </div>
                </div>
                <div className="w-1/2 p-4 border rounded-[35px] bg-[#D4D1D1]">
                  <div className="flex w-full justify-between">
                    <div>
                      <FlaskConical size={20} />
                    </div>
                    <div className="text-[10px] rounded-[10px] border border-black pl-1 pr-1">
                      Rs. 200
                    </div>
                  </div>
                  <div className="text-left">
                    <h1 className="font-semibold text-[15px] mb-2 mt-2">
                      In-house laboratory
                    </h1>
                    <p className="text-[11px] leading-tight">
                      Duis irure dolor inveniam nostrud reprehenderit
                      involuptatae
                    </p>
                  </div>
                </div>
              </div>
              <div className="h-1/2 flex gap-3">
                <div className="w-1/2 p-4 border rounded-[35px] bg-[#D4D1D1]">
                  <div className="flex w-full justify-between">
                    <div>
                      <FlaskConical size={20} />
                    </div>
                    <div className="text-[10px] rounded-[10px] border border-black pl-1 pr-1">
                      Rs. 200
                    </div>
                  </div>
                  <div className="text-left">
                    <h1 className="font-semibold text-[15px] mb-2 mt-2">
                      In-house laboratory
                    </h1>
                    <p className="text-[11px] leading-tight">
                      Duis irure dolor inveniam nostrud reprehenderit
                      involuptatae
                    </p>
                  </div>
                </div>
                <div className="w-1/2 p-4 border rounded-[35px] bg-[#D4D1D1]">
                  <div className="flex w-full justify-between">
                    <div>
                      <FlaskConical size={20} />
                    </div>
                    <div className="text-[10px] rounded-[10px] border border-black pl-1 pr-1">
                      Rs. 200
                    </div>
                  </div>
                  <div className="text-left">
                    <h1 className="font-semibold text-[15px] mb-2 mt-2">
                      In-house laboratory
                    </h1>
                    <p className="text-[11px] leading-tight">
                      Duis irure dolor inveniam nostrud reprehenderit
                      involuptatae
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Home;
