package com.JavaWebProject.JavaWebProject.repositories;

import com.JavaWebProject.JavaWebProject.models.Caterer;
import com.JavaWebProject.JavaWebProject.models.CateringOrder;
import com.JavaWebProject.JavaWebProject.models.Customer;
import com.JavaWebProject.JavaWebProject.models.Voucher;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CateringOrderRepository extends CrudRepository<CateringOrder, Integer> {
    
    int countDistinctCatererEmailByCreateDate(LocalDate date);
    
    int countDistinctCustomerEmailByCreateDate(LocalDate date);
    
    int countDistinctCustomerEmailByCreateDateBetween(LocalDate start, LocalDate end);
    
    @Query("select sum(u.pointDiscount) from CateringOrder u where u.createDate = ?1")
    Optional<Integer> sumPointDiscountByCreateDate(LocalDate date);
    
    int countByCreateDate(LocalDate date);
    
    int countByCreateDateBetween(LocalDate start, LocalDate end);
    
    @Query(value = "select count(*) from CateringOrder where year(CreateDate) = ?1", nativeQuery = true)
    int countByCreateDateYear(int year);
    
    List<CateringOrder> findAllByCatererEmail(Caterer CatererEmail);
    
    List<CateringOrder> findAllByCustomerEmail(Customer customer);
    
    List<CateringOrder> findAllByCatererEmailAndOrderState(Caterer caterer, String orderState);
    
    List<CateringOrder> findAllByCatererEmailAndCreateDate(Caterer caterer, LocalDate date);
    
    List<CateringOrder> findAllByCatererEmailAndCreateDateBetween(Caterer caterer, LocalDate startDate, LocalDate endDate);

    
    List<CateringOrder> findAllByCatererEmailAndOrderStateAndCreateDate(Caterer caterer,String orderState, LocalDate date);
    
    List<CateringOrder> findAllByCatererEmailAndOrderStateAndCreateDateBetween(Caterer caterer, String orderState, LocalDate startDate, LocalDate endDate);
    
    @Query("select max(u.orderID) from CateringOrder u")
    Optional<Integer> maxID();
    
    CateringOrder findByOrderID(Integer orderID);
    
    List<CateringOrder> findAllByVoucherIDAndCustomerEmail(Voucher voucherID, Customer customerEmail);
}
