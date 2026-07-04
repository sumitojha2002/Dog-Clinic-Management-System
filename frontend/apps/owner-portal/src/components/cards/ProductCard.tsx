import { Button } from "../ui/button";
import { ArrowRight } from "lucide-react";
import image from "../../assets/bowl-removebg-preview.png";

/*
    #FFE6F2
*/
function ProductCard() {
  return (
    <div>
      <div className=" relative">
        <div className=" h-50 bg-[#FFE6F2]  rounded-[20px] flex justify-center">
          <img className="rounded-[20px] h-50 " src={image} alt="" />
        </div>
        <div className="absolute top-3 right-4 pl-2 pr-2 rounded-[10px] text-[14px] bg-white">
          Rs. 100
        </div>
      </div>
      <div className="mt-3 flex flex-col gap-2">
        <h1 className="text-[20px] font-semibold">Small dog dish</h1>
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
