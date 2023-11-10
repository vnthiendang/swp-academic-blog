import { getAllAward } from "./Services/award.service.js";


//GET LIST AWARDS
const displayAwards = (awards) => {
    const awardList = document.querySelector(
      ".absolute.hidden.text-gray-700.pt-1.group-hover\\:block"
    );
  
    awards.forEach((award) => {
      const listItem = document.createElement("li");
      const link = document.createElement("a");
      link.className =
        "rounded-b bg-black hover:bg-gray-400 py-5 px-5 block whitespace-no-wrap text-white";
      link.href = `postByaward.html?awardId=${award.id}`;
      link.textContent = award.awardType;
  
      listItem.appendChild(link);
      awardList.appendChild(listItem);
    });
  };
  
  getAllAward().then((awards) => {
    displayAwards(awards);
  });