package com.ll.chatApp.domain.article.article.entity;

import com.ll.chatApp.domain.article.articleComment.entity.ArticleComment;
import com.ll.chatApp.domain.article.articleTag.entity.ArticleTag;
import com.ll.chatApp.domain.member.member.entity.Member;
import com.ll.chatApp.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class Article extends BaseEntity {
    String title;
    String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member author;

    // orphan(고아) 부모 관계 끊어지면 자동적으로 자식도 사라짐(부모는 남아있어도)
    // cascade는 부모가 사라질 때 같이 사라짐
    @OneToMany(mappedBy = "article", cascade = ALL, orphanRemoval = true)
    @Builder.Default //빌더 객체 만들 때 null point exception 방지

    private List<ArticleComment> comments = new ArrayList<>();

    public void addComment(Member memberAuthor, String commentBody) {
        ArticleComment comment = ArticleComment.builder()
                .article(this)
                .author(memberAuthor)
                .body(commentBody)
                .build();

        comments.add(comment);
    }

    public void removeComment(ArticleComment articleComment) {
        comments.remove(articleComment);
    }

    @OneToMany(mappedBy = "article", cascade = ALL, orphanRemoval = true)
    @Builder.Default
    private List<ArticleTag> tags = new ArrayList<>();

    public void addTag(String tagContent) {
        ArticleTag tag = ArticleTag.builder()
                .article(this)
                .content(tagContent)
                .build();

        tags.add(tag);
    }

    // ... 여려개의 작은 태그를 받아 올 때
    public void addTags(String... tagContents) {
        for( String tagContent : tagContents) {
            addTag(tagContent);
        }
    }

    public String getTagsStr() {
        String tagsStr = tags.stream()
                .map(ArticleTag::getContent)
                // 꺼내온 content를 #을 붙여 하나의 String으로 만들겠따.
                .collect(Collectors.joining("#"));
        if (tagsStr.isBlank())  {
            return "";
        }

        //가장 앞의 content에는 #d이 안 붙기 때문에 마치 태그처럼 보이게끔 붙여주기
        return "#" + tagsStr;

        // 예시
        // ["자바" , "스프링"] -> map에서꺼내옴
        // ["자바 #스프링"] -> joining
        // ["#자바 #스프링"] -> return의 #
    }

}
