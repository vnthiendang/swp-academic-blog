import { getAllAward } from "./Services/award.service.js";


//GET LIST AWARDS
const displayAwards = async () => {
  const awards = await getAllAward();

  const container = document.querySelector('.container');

  awards.forEach(award => {
    const card = document.createElement('div');
    card.classList.add('card');

    const cardHeader = document.createElement('div');
    cardHeader.classList.add('card__header');
    const cardImage = document.createElement('img');
    cardImage.src = 'https://source.unsplash.com/600x400/?computer';
    cardImage.alt = 'card__image';
    cardImage.classList.add('card__image');
    cardHeader.appendChild(cardImage);

    const cardBody = document.createElement('div');
    cardBody.classList.add('card__body');
    const tag = document.createElement('span');
    tag.classList.add('tag', 'tag-blue');
    tag.textContent = award.awardType;
    const heading = document.createElement('h4');
    const paragraph = document.createElement('p');
    cardBody.appendChild(tag);
    cardBody.appendChild(heading);
    cardBody.appendChild(paragraph);

    const cardFooter = document.createElement('div');
    cardFooter.classList.add('card__footer');
    const user = document.createElement('div');
    user.classList.add('user');
    const userImage = document.createElement('img');
    userImage.src = 'https://i.pravatar.cc/40?img=1';
    userImage.alt = 'user__image';
    userImage.classList.add('user__image');
    const userInfo = document.createElement('div');
    userInfo.classList.add('user__info');
    const userName = document.createElement('h5');
    const userSmall = document.createElement('small');

    const teacher = document.createElement('span');
    teacher.classList.add('teacher', 'teacher-blue');
    teacher.textContent = award.givenByUser;
    //userInfo.appendChild(userName);
    userInfo.appendChild(userSmall);
    user.appendChild(userImage);
    user.appendChild(userInfo);
    
    cardFooter.appendChild(user);
    cardFooter.appendChild(teacher);

    card.appendChild(cardHeader);
    card.appendChild(cardBody);
    card.appendChild(cardFooter);

    container.appendChild(card);
  });
};

displayAwards();