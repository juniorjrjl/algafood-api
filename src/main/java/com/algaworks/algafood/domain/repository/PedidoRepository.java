package com.algaworks.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import com.algaworks.algafood.domain.model.Pedido;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository  extends CustomJpaRepository<Pedido, Long>, 
    JpaSpecificationExecutor<Pedido>{

    @Query("from Pedido p join fetch p.cliente join fetch p.restaurante r join fetch r.cozinha")
    List<Pedido> findAll();
    
    Optional<Pedido> findByCodigo(String codigo);

    @Query("select case when "
          	 + "            count(1) > 0 then true "
          	 + "            else false "
          	 + "       end"
       	 + "  from Pedido p"
       	 + "  join p.restaurante r"
       	 + "  join r.usuarios u"
       	 + " where u.id = :idUsuario"
       	 + "   and p.codigo = :codigoPedido")
       boolean usuarioPodeGerenciarPedido(Long idUsuario, String codigoPedido);
    
}