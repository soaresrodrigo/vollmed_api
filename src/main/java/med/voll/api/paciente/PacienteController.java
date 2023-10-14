package med.voll.api.paciente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController // Ele transforma em um rest api
@RequestMapping("pacientes") // Define a rota para /pacientes
public class PacienteController {

	@Autowired // Injeção de dependências
	private PacienteRepository repository;

	@PostMapping // Transforma essa rota em post
	@Transactional // Tudo que for transação de dados, tipo, cadastrar, atualizar, excluir
	public void cadastrar(
			@RequestBody // Informa que os dados recebidos vão ser via request
			@Valid // Precisa passar para que ele pegue as validações dos DadosCadastroPaciente
			DadosCadastroPaciente dados) {
		repository.save(new Paciente(dados));
	}

	@GetMapping // Transforma essa rota em get
	public Page<DadosListagemPaciente> listar(Pageable paginacao) {
		return repository.findAll(paginacao).map(DadosListagemPaciente::new);
	}

	@GetMapping("/{id}")
	public Paciente detalhar(@PathVariable Long id) {
		return repository.findById(id).orElse(null);
	}

	@PutMapping("/{id}") // Transforma essa rota em put
	@Transactional // Pois ela vai trabalhar recebendo dados
	public void atualizar(
			@PathVariable Long id, // Recebe o parâmetro do id
			@RequestBody // receber dados
			@Valid // Passa pela validação dos DadosAtualizacaoPaciente
			DadosAtualizacaoPaciente dados) {
		var paciente = repository.getReferenceById(id);
		paciente.atualizarInformacoes(dados);
	}

	@DeleteMapping("/{id}") // Transforma essa rota em delete
	@Transactional // Vai modificar o banco
	public void excluir(@PathVariable Long id) {
		repository.deleteById(id); // Exclusão sem ser softDelete
	}

}
