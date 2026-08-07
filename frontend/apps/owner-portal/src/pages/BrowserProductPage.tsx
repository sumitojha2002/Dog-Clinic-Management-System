import { useQuery } from "@tanstack/react-query";
import React from "react";
import { getAllProducts } from "../services/api/authapi";
import ProductCard from "../components/cards/ProductCard";
import { useSearchParams } from "react-router-dom";

function BrowserProductPage() {
  const [searchParams] = useSearchParams();
  const search = searchParams.get("search") || "";
  const cat = searchParams.get("cat") || "";

  const {
    data: products,
    isLoading,
    isError,
  } = useQuery({
    queryKey: ["products", search, cat],
    queryFn: () => getAllProducts(search, cat),
    refetchOnWindowFocus: false, //  Stop refetching when user clicks back into the browser tab
    refetchOnReconnect: false, // Stop refetching when internet connection returns
    staleTime: Infinity, //  Prevent data from instantly marking itself old and auto-refetching
    gcTime: 1000 * 60 * 15, // Keep unused data in cache for 15 minutes before deleting
  });

  if (isLoading)
    return (
      <div className="flex justify-center w-full items-center">
        <div>loading...</div>
      </div>
    );

  if (isError)
    return (
      <div className="flex justify-center w-full items-center">
        <div>error has occured while fetching...</div>
      </div>
    );

  return (
    <div className="w-2/3 h-200">
      {products?.data && products.data.length > 0 ?
        <div className="grid grid-cols-2 gap-10">
          {products.data.map((pro) => (
            <div>
              <ProductCard
                key={pro.id}
                id={pro.id}
                name={pro.name}
                cover={pro.cover}
              />
            </div>
          ))}
        </div>
      : <div className="mt-45">
          <h1>No such product found.</h1>
        </div>
      }
    </div>
  );
}

export default BrowserProductPage;
