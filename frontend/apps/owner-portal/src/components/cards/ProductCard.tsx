import { Button } from "../ui/button";
import { ArrowRight } from "lucide-react";
import image from "../../assets/bowl-removebg-preview.png";
import type { productsInfo } from "../../services/api/apitypes";

function ProductCard({ id, name, cover }: productsInfo) {
  return (
    <div>
      <div className=" h-50 border-2 p-2 rounded-[20px] flex justify-center">
        <img className="rounded-[20px] h-45  " src={cover} alt="" />
      </div>
      <div className="mt-3 flex flex-col gap-2">
        <h1 className="text-[20px] font-semibold">{name}</h1>
        <div>
          <Button>Add to cart</Button>
        </div>
        <p className="flex justify-center items-center  text-[12px] gap-1">
          Buy now <ArrowRight size={10} />
        </p>
      </div>
    </div>
  );
}

export default ProductCard;
