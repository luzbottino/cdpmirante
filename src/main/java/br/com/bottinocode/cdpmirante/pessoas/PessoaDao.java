package br.com.bottinocode.cdpmirante.pessoas;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class PessoaDao extends Pessoa {

    @Getter
    @Setter
    private List<Telefone> telefones = new ArrayList<>();

    public PessoaDao(Pessoa pessoa, List<Telefone> telefones) {
        super(pessoa);
        this.telefones = telefones;
    }

}