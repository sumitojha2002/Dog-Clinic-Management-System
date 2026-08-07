import React, { useState } from "react";
import { getAllProductSubCategoryList } from "../../services/api/authapi";
import { useQuery } from "@tanstack/react-query";
import { Button } from "../ui/button";
import { useSearchParams } from "react-router-dom";

export default function FilterSideBar() {
  const [searchParams, setSearchParams] = useSearchParams();

  const [searchParam, setSearchParam] = useState<string>(
    searchParams.get("search") || "",
  );
  const [category, setCategory] = useState<string>(
    searchParams.get("cat") || "",
  );

  const sumbitSearch = () => {
    // 2. Pass the values directly to the URL parameters
    const newParams: Record<string, string> = {};
    if (searchParam) newParams.search = searchParam;
    if (category) newParams.cat = category;
    console.log(newParams);
    setSearchParams(newParams); // This updates the URL bar to: ?search=value&cat=value
  };
  
  const { data: subCategory } = useQuery({
    queryKey: ["subcategory"],
    queryFn: () => getAllProductSubCategoryList(),
  });


  return (
    <div className="w-1/3 ">
      <div className=" p-10  flex-col border">
        <div>
          <h1 className="text-2xl p-4">Dog Love</h1>
        </div>
        <div className="flex-col ">
          <div className="flex flex-col gap-2">
            <div className="w-full flex justify-start">
              <label className="text-[15px] text-gray-600 ">
                name of product
              </label>
            </div>
            <div className="flex justify-center">
              <input
                type="text"
                className="border pl-2"
                placeholder="name of the product"
                value={searchParam}
                onChange={(e) => setSearchParam(e.target.value)}
              />
              <Button className="rounded-none" onClick={() => sumbitSearch()}>
                Search
              </Button>
            </div>
          </div>
        </div>
        <div className="flex flex-col items-start mt-5 gap-2">
          <label className="text-[15px] text-gray-600">category</label>
          <div className="flex justify-center w-full">
            {subCategory?.data && subCategory.data.length > 0 ?
              <select
                className="border"
                value={category}
                onChange={(e) => setCategory(e.target.value)}
              >
                <option value="" disabled selected className="text-gray-500">
                  Select an option
                </option>
                {subCategory.data.map((p) => (
                  <option value={p.id}>{p.name}</option>
                ))}
                <option value="">None</option>
              </select>
            : <h1 className="text-red-500">*unable to fetch</h1>}
          </div>
        </div>
        <div className="mt-10">
          <Button
            className="rounded-none w-full"
            onClick={() => sumbitSearch()}
          >
            Search
          </Button>
        </div>
      </div>
    </div>
  );
}
