package com.initsan.myToDoList.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "lists")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Where(clause = "rmv = 0")
@SQLDelete(sql = "UPDATE lists SET rmv = 1 WHERE id = ?", check = ResultCheckStyle.COUNT)
public class TList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    @NotNull
    private String name;

    @Column
    @NotNull
    private int rmv;

    @Column(name = "user_id", nullable = false)
    @NotNull
    private Long userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TList tList)) return false;
        return name.equals(tList.name) && userId.equals(tList.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, userId);
    }

}
