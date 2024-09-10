
# SPRING-LOJA-TESTES-AUTOMATIZADOS
Sistema que simula o gerenciamento de vendas de uma loja de roupas com testes automatizados únicos e de integração.

## Regras de Negócio e Requisitos
## Regras Gerais
- Campo de Observação: A observação da venda é um campo obrigatório.
## Associação de Vendas:
- Uma venda deve sempre estar associada a um cliente.
- Uma venda pode ou não estar associada a um funcionário.
- A lista de produtos associados a uma venda jamais pode estar vazia.
- Informações do Produto:
- O nome do produto é um campo obrigatório.
- A descrição do produto é opcional.
## Regras de Validação
### Validação de Idade:
- As idades dos clientes e funcionários não podem ser negativas.
### Endereço:
- O endereço é um campo obrigatório tanto para clientes quanto para funcionários.
### E-mail:
- O e-mail de um cliente ou funcionário deve ser válido.
### CPF:
- O CPF (Cadastro de Pessoas Físicas) de um cliente ou funcionário deve ser válido.
### CEP:
- O CEP (Código de Endereçamento Postal) do cliente deve seguir o padrão brasileiro.
### Nome do Cliente:
- O nome do cliente deve conter pelo menos duas palavras separadas por um espaço.
### Números de Telefone:
- Os números de telefone devem seguir o padrão:
(XX) XXXX-XXXX ou
(XX) XXXXX-XXXX.
## Restrições de Vendas
### Restrições para Clientes Menores de Idade:
- Ao salvar uma venda, caso o cliente tenha menos de 18 anos, o valor total da venda não deve exceder 500 reais.
- Se o valor total ultrapassar este montante, a venda não deve ser persistida, e uma RuntimeException deve ser lançada com a mensagem: "não pode comprar acima de 500 reais".

## Tecnologias Usadas
<div >
	<code><img width="30" src="https://user-images.githubusercontent.com/25181517/192109061-e138ca71-337c-4019-8d42-4792fdaa7128.png" alt="Postman" title="Postman"/></code>
	<code><img width="30" src="https://user-images.githubusercontent.com/25181517/117201156-9a724800-adec-11eb-9a9d-3cd0f67da4bc.png" alt="Java" title="Java"/></code>
	<code><img width="30" src="https://user-images.githubusercontent.com/25181517/117201470-f6d56780-adec-11eb-8f7c-e70e376cfd07.png" alt="Spring" title="Spring"/></code>
	<code><img width="30" src="https://user-images.githubusercontent.com/25181517/183891303-41f257f8-6b3d-487c-aa56-c497b880d0fb.png" alt="Spring Boot" title="Spring Boot"/></code>
	<code><img width="30" src="https://user-images.githubusercontent.com/25181517/117207242-07d5a700-adf4-11eb-975e-be04e62b984b.png" alt="Maven" title="Maven"/></code>
	<code><img width="30" src="https://user-images.githubusercontent.com/25181517/117533873-484d4480-afef-11eb-9fad-67c8605e3592.png" alt="JUnit" title="JUnit"/></code>
	<code><img width="30" src="https://user-images.githubusercontent.com/25181517/183892181-ad32b69e-3603-418c-b8e7-99e976c2a784.png" alt="mocikto" title="mocikto"/></code>
	<code><img width="30" src="https://user-images.githubusercontent.com/25181517/190229463-87fa862f-ccf0-48da-8023-940d287df610.png" alt="Lombok" title="Lombok"/></code>
</div>
