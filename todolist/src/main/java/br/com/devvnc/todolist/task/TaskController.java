package br.com.devvnc.todolist.task;

import br.com.devvnc.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request){
        var idUser = request.getAttribute("idUser");
        taskModel.setIdUser((UUID) idUser);

        var currentDate = LocalDateTime.now();
        if (currentDate.isAfter(taskModel.getStartTask()) || currentDate.isAfter(taskModel.getEndTask())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The actual date needs to be behind start date and end date");
        }

        if (taskModel.getStartTask().isAfter(taskModel.getEndTask())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The actual date is before End date");
        }

        var task = this.taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(task);

    }

    @GetMapping("/")
    public List<TaskModel> list(HttpServletRequest request){
        var idUser = request.getAttribute("idUser");
        var tasks = this.taskRepository.findByIdUser((UUID) idUser);
        return tasks;
    }

    @PutMapping("/{id}")
    public TaskModel update(@RequestBody TaskModel taskModel, HttpServletRequest request,@PathVariable UUID id){
        var task = this.taskRepository.findById(id).orElse(null);
        Utils.copyNullProperties(taskModel, task);

        return this.taskRepository.save(task);
    }
}
