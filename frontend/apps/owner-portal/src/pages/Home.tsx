import { Button } from "../components/ui/button";
import vetone from "../assets/vet1-removebg-preview.png";
import vettwo from "../assets/vet2-removebg-preview.png";
import { FlaskConical } from "lucide-react";
import ProductCard from "../components/cards/ProductCard";
import VetCard from "../components/cards/VetCard";
import { useQuery } from "@tanstack/react-query";
import AboutDog from "../../src/assets/AboutImageDog.jpg";
import BirthdayBoi from "../../src/assets/birthdayboi.jpg";
import { getVetsCardForHome } from "../services/api/authapi";

function Home() {
  const { data } = useQuery({
    queryKey: ["vetsCard"],
    queryFn: () => getVetsCardForHome(),
    staleTime: Infinity,
    refetchOnMount: false,
    refetchOnReconnect: false,
    refetchOnWindowFocus: false,
  });

  return (
    <div className="mt-20">
      <div>
        <div className="w-full h-170 rounded-[45px] bg-[#D0A7DE] flex mb-40">
          <div className="w-1/2 flex flex-col justify-center text-left gap-10 pl-9">
            <div className="text-6xl flex flex-col gap-2">
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
            <img height={500} width={500} src={vetone} alt="image" />
          </div>
        </div>
        <div className="h-90 mt-40">
          <div className=" w-full h-12 mb-10">
            <div className="text-center">
              <h1 className="text-4xl mt-10 mb-10 font-semibold">
                Our Service
              </h1>
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
                    <div className="text-[10px]  rounded-[10px] border border-black pl-1 pr-1">
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

        {/* hello */}
        <div className=" mt-60 mb-20">
          <div>
            <h1 className="text-4xl mt-10 mb-10 font-semibold">
              Our featured products
            </h1>
            <p className="text-[16px] flex flex-col">
              <span>Lorem ipsum dolor sit amet consectetur adipisicing</span>
              <span>
                elit. Doloribus dolores sit eos esse at omnis nemo ullam. Nobis
                et laudantium
              </span>
            </p>
          </div>
          <div className="grid grid-cols-3 gap-20  m-10">
            <ProductCard />
            <ProductCard />
            <ProductCard />
          </div>
        </div>
      </div>

      <div className=" mt-30 mb-20 grid grid-cols-2 gap-2">
        <div className="flex flex-col justify-between">
          <img
            src={AboutDog}
            alt=""
            className="h-120 object-cover rounded-2xl"
          />
          <div className="flex justify-center items-center">
            <p className="p-5">
              Lorem ipsum dolor sit, amet consectetur adipisicing elit. Illum ad
              excepturi provident culpa tempora fugiat exercitationem dolores
              obcaecati expedita natus aperiam commodi non sed repellendus
              deserunt, labore assumenda. Vel, magnam.
            </p>
          </div>
        </div>
        <div className="flex flex-col justify-between">
          <div className="flex flex-col justify-center">
            <h1 className="text-4xl mt-10 mb-10 font-semibold">About Us</h1>
            <p className="p-5">
              Lorem ipsum dolor sit amet consectetur adipisicing elit. Aliquid
              debitis quo eos odio, id reprehenderit corrupti animi enim
              eveniet. Eos animi assumenda eaque repellendus laborum magnam
              ipsam, recusandae asperiores atque!
            </p>
          </div>

          <img
            src={BirthdayBoi}
            alt=""
            className="w-full object-cover rounded-2xl"
          />
        </div>
      </div>

      <div className=" mt-40 mb-20">
        <div>
          <h1 className="text-4xl mt-10 mb-14 font-semibold flex justify-center flex-col">
            <span>Meet the team that will take</span>
            <span>care of your little friend</span>
          </h1>
        </div>
        <div className="grid grid-cols-3 gap-5 pl-10 pr-10">
          {data?.data &&
            data.data.map((vet) => <VetCard key={vet.vetId} {...vet} />)}
        </div>
      </div>
    </div>
  );
}

export default Home;
