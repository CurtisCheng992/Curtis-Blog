package com.curtis.curtisblog.vo;

import com.curtis.curtisblog.entity.Type;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogQuery {

    private String title;
    private Long typeId;
    private Boolean recommend;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Boolean getRecommend() {
        return recommend;
    }

    public void setRecommend(Boolean recommend) {
        this.recommend = recommend;
    }
}
