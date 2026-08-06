import { Button } from "../ui/button";
import { ArrowRight } from "lucide-react";
import image from "../../assets/bowl-removebg-preview.png";
import type { productsInfo } from "../../services/api/apitypes";
import { useNavigate } from "react-router-dom";

function ProductCard({ id, name, cover }: productsInfo) {
  const navigate = useNavigate();

  return (
    <div>
      <div className=" h-50 border-2 p-2 rounded-[20px] flex justify-center">
        <img className="h-45" src={cover} alt="" />
      </div>
      <div className="mt-3 flex flex-col gap-2">
        <h1 className="text-[20px] font-semibold">{name}</h1>
        <div>
          <Button onClick={() => navigate(`/products/${id}/details`)}>
            View Product
          </Button>
        </div>
      </div>
    </div>
  );
}

export default ProductCard;
