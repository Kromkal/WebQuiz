package com.example.mywebquizengine.Repos;

import com.example.mywebquizengine.Model.Chat.Dialog;
import com.example.mywebquizengine.Model.Projection.DialogWithUsersView;
import com.example.mywebquizengine.Model.Projection.DialogWithUsersViewPaging;
import com.example.mywebquizengine.Model.Test.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;

public interface DialogRepository extends CrudRepository<Dialog, Long>, JpaRepository<Dialog, Long>,
        PagingAndSortingRepository<Dialog, Long> {

    /*
    Если при группировке по идентификатору для выбранных пользователей результат группировки - 2
    (то есть было 2 записи с одинаковым id диалога)
    то диалог существует и возвращается его идентификатор
     */
    @Query(value = "SELECT DIALOG_ID, COUNT(DIALOG_ID)\n" +
            "FROM USERS_DIALOGS a\n" +
            "WHERE (USER_ID = :firstUser or USER_ID = :secondUser) AND\n" +
            "      (SELECT COUNT(DIALOG_ID) FROM USERS_DIALOGS b where b.DIALOG_ID = a.DIALOG_ID) = 2\n" +
            "GROUP BY DIALOG_ID HAVING COUNT(DIALOG_ID) = 2", nativeQuery = true)
    Long findDialogByName(String firstUser, String secondUser);

    DialogWithUsersView findDialogByDialogId(Long id);



    DialogWithUsersViewPaging findAllDialogByDialogId(Long id);


}
