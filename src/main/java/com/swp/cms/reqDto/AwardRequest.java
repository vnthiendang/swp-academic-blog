package com.swp.cms.reqDto;

import lombok.Data;

@Data
public class AwardRequest {

        private Integer id;
        private Integer awardTypeID;
        private Integer postID;
        private Integer givenByUserID;

}
