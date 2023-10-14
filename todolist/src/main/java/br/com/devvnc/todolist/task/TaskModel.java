package br.com.devvnc.todolist.task;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Data
@Entity(name = "tb_tasks")
public class TaskModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String description;

    @Column(length = 50)
    private String title;
    private LocalDateTime startTask;
    private LocalDateTime endTask;
    private String priority;
    private UUID idUser;

    @CreationTimestamp
    private  LocalDateTime createdAt;

    /**E importante passar o 'throws Exception' para passar
     * a Excessao criada para a camada superior
     * */
    public void setTitle(String title) throws Exception{
        if(title.length() > 50){
            throw new Exception("Maximium of chars. in title field is 50.");
        }
        this.title = title;
    }


}
