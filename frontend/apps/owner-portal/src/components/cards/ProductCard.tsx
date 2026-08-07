import { Button } from "../ui/button";
import image from "../../assets/bowl-removebg-preview.png";
import type { productsInfo } from "../../services/api/apitypes";
import { useNavigate } from "react-router-dom";

function ProductCard({ id, name, cover }: productsInfo) {
  const navigate = useNavigate();

  return (
    <div className="rounded-2xl border p-2">
      <div className=" h-50  p-2  flex justify-center ">
        <img className="h-45" src={cover} alt="" />
      </div>
      <div className="mt-3 flex flex-col gap-2 border rounded-2xl p-2">
        <h1 className="text-[20px] font-semibold">{name}</h1>
        <div>
          <Button onClick={() => navigate(`/products/${id}`)}>
            View Product
          </Button>
        </div>
      </div>
    </div>
  );
}

export default ProductCard;
