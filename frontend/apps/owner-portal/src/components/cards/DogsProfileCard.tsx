import { Button } from "../ui/button";

interface DogsInfo {
  id: number;
  imageURL: string | null;
  name: string;
  dateOfBirth: string;
  breed: string;
}

function DogsProfileCard(dogData: DogsInfo) {
  return (
    <div className="flex justify-center">
      <div className="border w-75 h-90">
        <div className="flex justify-center  h-50 items-center">
          <img
            src={dogData.imageURL ?? ""}
            alt=""
            className="border w-40 h-40 rounded-[100px] object-cover"
          />
        </div>
        <div className="flex flex-col p-3 gap-4">
          <div>
            <div className="grid grid-cols-2">
              <label className="">Name:</label>
              <h3>{dogData.name}</h3>
            </div>
            <div className="grid grid-cols-2">
              <label className="">Breed:</label>
              <h3>{dogData.breed}</h3>
            </div>
            <div className="grid grid-cols-2 ">
              <label className="">Date of Birth:</label>
              <h3>{dogData.dateOfBirth}</h3>
            </div>
          </div>
          <Button>View Profile</Button>
        </div>
      </div>
    </div>
  );
}

export default DogsProfileCard;
