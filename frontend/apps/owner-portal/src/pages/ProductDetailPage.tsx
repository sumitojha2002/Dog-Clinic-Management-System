import { useQuery } from "@tanstack/react-query";
import { useNavigate, useParams } from "react-router-dom";
import { getProductDetail } from "../services/api/authapi";
import { ArrowLeft, Divide, Ghost } from "lucide-react";
import { Button } from "../components/ui/button";
import { useState } from "react";

export default function ProductDetailPage() {
  const { id } = useParams();
  const [num, setNum] = useState<number>(0);
  const navigate = useNavigate();
  const { data: product } = useQuery({
    queryKey: ["prodct", id],
    queryFn: () => getProductDetail(id),
    refetchOnWindowFocus: false, //  Stop refetching when user clicks back into the browser tab
    refetchOnReconnect: false, // Stop refetching when internet connection returns
    staleTime: Infinity, //  Prevent data from instantly marking itself old and auto-refetching
    gcTime: 1000 * 60 * 15, // Keep unused data in cache for 15 minutes before deleting
  });

  return (
    <div className="mt-10">
      <div className="ml-5 mb-5">
        <ArrowLeft
          size={20}
          className="cursor-pointer"
          onClick={() => navigate(-1)}
        />
      </div>
      {product?.data && product.data != null ?
        <div className="grid grid-cols-2">
          <div>
            <img
              src={product.data.cover}
              className="h-150 border p-2 object-fill"
            />
          </div>
          <div className=" flex-1 p-10 text-start">
            <h1 className="text-4xl font-bold">{product.data.name}</h1>
            <div className="mt-4 flex-1">
              <h1 className="text-[20px] mb-2 font-semibold">Description</h1>
              <p className="font-light">{product.data.description}</p>
            </div>
            <div className="mt-4 flex-1">
              <h1 className="text-[20px] mb-2 font-semibold">Summary</h1>
              <p className="font-light">{product.data.summery}</p>
            </div>
            <div className="mt-4 flex-1">
              <h1 className="text-[20px] mb-2 font-semibold">
                Additional Information
              </h1>
              {(
                product.data.productsSkus &&
                product.data.productsSkus.length > 0
              ) ?
                <div className="grid grid-cols-2 gap-2">
                  {product.data.productsSkus.map((p) => (
                    <div className="flex-1 gap-2 border p-3">
                      <div className="font-semibold mb-2">{p.sku}</div>
                      <div className="grid grid-cols-2">
                        <div className="font-semibold">Color</div>
                        <div>{p.colorAttributes.value}</div>
                      </div>
                      <div className="grid grid-cols-2">
                        <div className="font-semibold">Size</div>
                        <div>{p.sizeAttributes.value}</div>
                      </div>
                      <div className="grid grid-cols-2">
                        <div className="font-semibold">Price</div>
                        <div className="font-semibold">Rs {p.price}</div>
                      </div>
                      <div className="grid grid-cols-2"></div>
                    </div>
                  ))}
                </div>
              : <div className="text-center">
                  <p className="text-red-400">
                    *product information not provided at the moment
                  </p>
                </div>
              }
            </div>
            <div className="grid grid-cols-2">
              <div>
                {product.data.productsSkus &&
                  product.data.productsSkus.length > 0 && (
                    <h1 className="text-[20px] mb-2 font-semibold mt-5">
                      Size
                    </h1>
                  )}
                {(
                  product.data.productsSkus &&
                  product.data.productsSkus.length > 0
                ) ?
                  <div className="flex justify-center">
                    <select name="size" id="" className="border text-center">
                      {product.data.productsSkus.map((p) => (
                        <option value={p.sizeAttributes.value}>
                          {p.sizeAttributes.value}
                        </option>
                      ))}
                    </select>
                  </div>
                : <></>}
              </div>
              <div>
                {product.data.productsSkus &&
                  product.data.productsSkus.length > 0 && (
                    <h1 className="text-[20px] mb-2 font-semibold mt-5">
                      Quantity
                    </h1>
                  )}
                {product.data.productsSkus &&
                  product.data.productsSkus.length > 0 && (
                    <div className="grid grid-cols-3 gap-2">
                      <Button
                        className="rounded-none"
                        onClick={() => setNum(num + 1)}
                      >
                        +
                      </Button>
                      <input
                        type="text"
                        className="border text-center"
                        value={num}
                      />
                      <Button
                        className="rounded-none"
                        onClick={() => setNum(num - 1)}
                        disabled={num == 0}
                      >
                        -
                      </Button>
                    </div>
                  )}
              </div>
            </div>
            <div className="mt-10 w-full grid grid-cols-2 gap-2">
              <Button className="rounded-none" disabled={num == 0} onClick={()=>}>
                Add to Cart
              </Button>
              <Button className="rounded-none border-2" variant={"ghost"}>
                ADD TO WISHLIST
              </Button>
            </div>
          </div>
        </div>
      : <div className="h-100 flex justify-center items-center border">
          <h1>Product infomation not found.</h1>
        </div>
      }
    </div>
  );
}
