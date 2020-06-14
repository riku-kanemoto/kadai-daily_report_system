package models;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
@Table(name="follow")
@NamedQueries({
    @NamedQuery(
            name="getAllFollowEmployees",
            query="SELECT f.follow_id FROM Follow AS f WHERE f.user_id=:user_id AND f.follow_flag=1 ORDER BY f.id DESC"
            ),
    @NamedQuery(
            name="getDestroyFollow",
            query="SELECT f FROM Follow AS f WHERE f.user_id=:user_id AND f.follow_id=:follow_id"
            )
})
@Entity
public class Follow {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name="user_id",nullable=false)
    private Employee user_id;
    @ManyToOne
    @JoinColumn(name="follow_id",nullable=false)
    private Employee follow_id;

    @Column(name="follow_at",nullable=false)
    private Timestamp follow_at;

    @Column(name="follow_flag",nullable=false)
    private Integer follow_flag;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }




    public Employee getUser_id() {
        return user_id;
    }

    public void setUser_id(Employee user_id) {
        this.user_id = user_id;
    }

    public Employee getFollow_id() {
        return follow_id;
    }

    public void setFollow_id(Employee follow_id) {
        this.follow_id = follow_id;
    }

    public Timestamp getFollow_at() {
        return follow_at;
    }

    public void setFollow_at(Timestamp follow_at) {
        this.follow_at = follow_at;
    }

    public Integer getFollow_flag() {
        return follow_flag;
    }

    public void setFollow_flag(Integer follow_flag) {
        this.follow_flag = follow_flag;
    }

}
