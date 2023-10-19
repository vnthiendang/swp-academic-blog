import { getAllTag } from "./Services/tag.service.js";

  //GET LIST TAGS
  const displayTags = (tags) => {
    const categoryList = document.querySelector('.grid.grid-cols-2.gap-4.flex-wrap');
  
    tags.forEach(tag => {
      const link = document.createElement('a');
      link.href = tag.url ?? 'http://localhost:8080/blog/tag/tagId';
      link.innerHTML = `<div class="rounded-2xl bg-gray-300 py-1 text-center">${tag.tagName}</div>`;
      categoryList.appendChild(link);
    });
  };
  
  getAllTag()
    .then((tags) => {
      displayTags(tags);
  });