# Projeto de Infraestrutura de Comunicação (IF678)

Projeto da Cadeira de Infraestrutura de Comunicação (IF678) - Aplicação segura de chat e transferência de arquivos

Prof.: Paulo Gonçalves <br />
Alunos: <br />
■ Jonatas de Oliveira Clementino (joc) - https://github.com/JonatasDeOliveira <br />
■ Lucas Alves Rufino (lar) - https://github.com/Lucas-Rufino <br />
■ Miriane da Silva Trajano Nascimento (mstn)  <br />
■ Rodrigo de Lima Oliveira (rlo) - https://github.com/rllima  <br />
■ Ullayne Fernandes Farias de Lima (uffl) - https://github.com/ullayne02  <br />
■ Valdemiro Rosa Vieira Santos (vrvs) - https://github.com/vrvs  <br />
Projeto 2016 - 2 <br />
Peso: 30% <br />
Especificação: Versão 2.0 de 2 4/11/201 6 <br />

## 1. Objetivo

O objetivo deste projeto é o
desenvolvimento de uma aplicação segura
de chat e transferência de arquivos que
utilize exclusivamente uma arquitetura
cliente/servidor. Todas as mensagens do
chat e arquivos são trocados entre usuários
com o auxílio de uma nuvem aqui
representada por um servidor. Esta nuvem
armazena temporariamente todos os dados
(mensagens do chat e arquivos) a serem
trocados, entregando-os ao usuário correto
quando ele estiver disponível online. A
aplicação deverá utilizar TCP para a
transferência confiável de dados e
criptografia para manter o sigilo das
informações trafegadas na rede. Os
requisitos mínimos do projeto estão explicitados no texto que segue.


## 2. Regras mínimas a serem observadas:

■ a implementação poderá ser em qualquer linguagem. Contudo a linguagem
Java é recomendada por haver um melhor suporte da monitoria; <br />
■ a aplicação deverá possuir interface gráfica tanto do lado dos usuários quanto no
servidor; <br />
■ o servidor deverá mostrar em sua interface gráfica em tempo real, a lista de
usuários conectados ao serviço, o endereço IP de cada um e um status (se há ou
não mensagens/arquivos pendentes para aquele usuário); <br />
■ em uma transferência de arquivo, seja do usuário ao servidor seja do servidor ao
usuário, o servidor e a aplicação do usuário deverão ter uma barra mostrando, em
tempo real, o percentual já enviado/recebido do arquivo, uma estimativa de tempo
para término da transferência e uma estimativa de RTT entre eles; <br />
■ o servidor deverá suportar ao menos 3 usuários simultaneamente. Então, para fins
de demonstração do projeto são necessários ao menos 4 computadores; <br />
■ o servidor só guarda mensagens e arquivos enquanto não forem entregues e lidas
pelo destinatário; <br />
■ Arquivos e mensagens de usuários devem ser criptografados, só podendo ser
decifrados pelo usuário receptor. Conceitos básicos de criptografia são
apresentados no livro do Kurose e alguns exemplos de criptografia usando java
são encontrados em http://www.devmedia.com.br/utilizando-criptografia-simetrica-
em-java/ <br />
■ O algoritmo de criptografia (e consequente API) a ser usado é uma decisão do
grupo e a qualidade do algoritmo escolhido não influencia na avaliação do projeto;
■ o servidor não poderá saber como decifrar mensagens (de chat) e arquivos
armazenados; <br />
■ o servidor deve usar o modelo store and foward ao receber mensagens de chat e
arquivos; <br />
■ a aplicação deverá permitir ao usuário saber o status de envio, entrega e
leitura/visualização de arquivos e mensagens. Podem se inspirar no whatsapp;
■ a aplicação do usuário deverá permiti-lo configurar através da interface gráfica o IP
ou nome do servidor; <br />
■ a aplicação cliente deverá permitir ao usuário “iniciar/pausar/cancelar/reiniciar”
o download. O cancelamento do download implica na deleção do trecho já
baixado. O download deverá sempre continuar de onde parou quando reiniciado
(ou do zero caso o trecho tenha sido apagado); <br />
■ A aplicação deverá suportar qualquer tipo de arquivo de até 1Gb. Note que a
depender da técnica criptográfica, o tamanho do arquivo criptografado poderá ser
maior do que o tamanho do arquivo original; <br />
■ Pelo menos 1 computador do usuário deverá ter wireskark instalado para o grupo
mostrar pacotes contendo mensagens criptografadas. Considerem a possibilidade
de trazer um notebook caso não seja possível rodar o sniffer nos computadores
dos GRADs; <br />
■ o protocolo para prover confiabilidade é o TCP; <br />
■ Item bônus (implementação não obrigatória): a aplicação deverá suportar
formação de grupo (>= 3 usuários para fins de demonstração) e troca de
mensagens/arquivos dentro do grupo. O bônus pode adicionar até 3,34 pontos à
nota do projeto desde que os demais requisitos expostos tenham sido
contemplados de forma satisfatória. Assim, se o grupo tirar 10 no projeto e ainda
ganhar o bônus de 3,34, a média obtida será 13,34*30%= 4,00; <br />
■ serão aceitos grupos de até 6 integrantes. Uma lista citando os integrantes do
grupo deverá ser entregue em sala de aula até o dia 09/11/ 16 ; <br />
■ o trabalho deverá ser apresentado dia 09/12 (qualquer GRAD) e todos os
integrantes deverão participar; <br />
■ entregar um relatório que:
```
a) especifique a linguagem e APIs importantes adotadas no projeto;
b) detalhe todas as regras de funcionamento dos protocolos implementados
como a máquina de estados (ou fluxo de mensagens para cada situação),
o tipo, o formato e a semântica das mensagens;
c) apresente claramente o overhead dos protocolos desenvolvidos, ou seja, o
que não é dado útil de usuário;
d) mostre como foi foram feitos os cálculos da estimativa de RTT e de tempo
para término da transferência de arquivos;
e) explicite as diferenças entre o protocolo apresentado no relatório e o
protocolo efetivamente implementado;
f) apresente as dificuldades encontradas ao longo do desenvolvimento do
projeto.
```
## 3. Avaliação

Serão observados os seguintes itens para a atribuição da nota ao projeto:
■ Se o software está realmente funcional; <br />
■ Se os critérios mínimos definidos foram atendidos; <br />
■ A qualidade da apresentação (não é preciso fazer ppt); <br />
■ A qualidade da implementação; <br />
■ A qualidade do relatório; <br />
■ Acompanhamento do projeto pelo monitor do grupo; <br />
■ A presença e participação na apresentação do trabalho; <br />
■ A participação no desenvolvimento do trabalho. <br />

## 4. Dicas

■ Não deixe para começar o projeto mais tarde. Comece logo e no mais tardar dia
04 / 11 /201 6 :-) <br />
■ Impossível fazer o projeto de “virada” ... mesmo em duas semanas de “viradas” ;-) <br />
■ Fazer um cronograma de atividades de desenvolvimento do projeto. Considere
que está na época de provas e potencialmente de desenvolvimento de outros
projetos em outras disciplinas; <br />
■ Começar o trabalho definindo os protocolos de comunicação e planejando como
será a implementação. Assim, fica mais fácil saber o que implementar e como. O
tempo de implementação também é minimizado; <br />
■ Não esquecer de dar atenção ao relatório! A entrega do mesmo deverá ser no dia
da apresentação, mas antes de começá-la; <br />
■ Os monitores acompanharão o desenvolvimento dos projetos. O monitor do seu
grupo será definido até dia 11 / 11. Por isso, não esqueça de entregar em sala a
lista com o nome dos componentes de seu grupo; <br />
■ Haverá 03 marcos de acompanhamento do projeto: 1º até 2 5 / 11 , 2º até 02 / 12 e 3º
até 07 / 12. Isto significa que até cada uma dessas datas o grupo deverá mostrar ao
monitor responsável pela equipe o status atual de desenvolvimento do projeto. <br />
■ A responsabilidade de ter acompanhamento no projeto é do grupo e não do
monitor. Assim, não espere que os monitores vão “correr atrás” dos grupos. <br />
■ Cuidado ao dividir as tarefas de implementação do projeto entre os componentes
do grupo. Será necessário integrar tudo depois e isso leva tempo (tipicamente não
menos de 1 semana a 2 semanas a depender da equipe)! <br />
■ Lembrem-se da importância do projeto! São até 3 pontos na média parcial (ou 4
com o bônus)! <br />
■ Certamente a especificação apresentada deixa pontos em aberto. Dúvidas?
Pergunte em sala! Suas dúvidas podem ser também as de outros alunos! <br />


