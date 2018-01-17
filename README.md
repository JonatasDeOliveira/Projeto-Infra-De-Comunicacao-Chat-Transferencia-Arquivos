# Projeto de Infraestrutura de Comunicação (IF678)

Projeto da Cadeira de Infraestrutura de Comunicação (IF678) - Aplicação segura de chat e transferência de arquivos

Prof.: Paulo Gonçalves
Alunos: 
■ Jonatas de Oliveira Clementino (joc) - https://github.com/JonatasDeOliveira 
■ Lucas Alves Rufino (lar) - https://github.com/Lucas-Rufino
■ Miriane da Silva Trajano Nascimento (mstn) 
■ Rodrigo de Lima Oliveira (rlo) - https://github.com/rllima 
■ Ullayne Fernandes Farias de Lima (uffl) - https://github.com/ullayne02 
■ Valdemiro Rosa Vieira Santos (vrvs) - https://github.com/vrvs 
Projeto 2016 - 2
Peso: 30%
Especificação: Versão 2.0 de 2 4/11/201 6

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
Java é recomendada por haver um melhor suporte da monitoria;
■ a aplicação deverá possuir interface gráfica tanto do lado dos usuários quanto no
servidor;
■ o servidor deverá mostrar em sua interface gráfica em tempo real, a lista de
usuários conectados ao serviço, o endereço IP de cada um e um status (se há ou
não mensagens/arquivos pendentes para aquele usuário);
■ em uma transferência de arquivo, seja do usuário ao servidor seja do servidor ao
usuário, o servidor e a aplicação do usuário deverão ter uma barra mostrando, em
tempo real, o percentual já enviado/recebido do arquivo, uma estimativa de tempo
para término da transferência e uma estimativa de RTT entre eles;
■ o servidor deverá suportar ao menos 3 usuários simultaneamente. Então, para fins
de demonstração do projeto são necessários ao menos 4 computadores;
■ o servidor só guarda mensagens e arquivos enquanto não forem entregues e lidas
pelo destinatário;
■ Arquivos e mensagens de usuários devem ser criptografados, só podendo ser
decifrados pelo usuário receptor. Conceitos básicos de criptografia são
apresentados no livro do Kurose e alguns exemplos de criptografia usando java
são encontrados em http://www.devmedia.com.br/utilizando-criptografia-simetrica-
em-java/
■ O algoritmo de criptografia (e consequente API) a ser usado é uma decisão do
grupo e a qualidade do algoritmo escolhido não influencia na avaliação do projeto;
■ o servidor não poderá saber como decifrar mensagens (de chat) e arquivos
armazenados;
■ o servidor deve usar o modelo store and foward ao receber mensagens de chat e
arquivos;
■ a aplicação deverá permitir ao usuário saber o status de envio, entrega e
leitura/visualização de arquivos e mensagens. Podem se inspirar no whatsapp;
■ a aplicação do usuário deverá permiti-lo configurar através da interface gráfica o IP
ou nome do servidor;
■ a aplicação cliente deverá permitir ao usuário “iniciar/pausar/cancelar/reiniciar”
o download. O cancelamento do download implica na deleção do trecho já
baixado. O download deverá sempre continuar de onde parou quando reiniciado
(ou do zero caso o trecho tenha sido apagado);
■ A aplicação deverá suportar qualquer tipo de arquivo de até 1Gb. Note que a
depender da técnica criptográfica, o tamanho do arquivo criptografado poderá ser
maior do que o tamanho do arquivo original;
■ Pelo menos 1 computador do usuário deverá ter wireskark instalado para o grupo
mostrar pacotes contendo mensagens criptografadas. Considerem a possibilidade
de trazer um notebook caso não seja possível rodar o sniffer nos computadores
dos GRADs;
■ o protocolo para prover confiabilidade é o TCP;
■ Item bônus (implementação não obrigatória): a aplicação deverá suportar
formação de grupo (>= 3 usuários para fins de demonstração) e troca de
mensagens/arquivos dentro do grupo. O bônus pode adicionar até 3,34 pontos à
nota do projeto desde que os demais requisitos expostos tenham sido
contemplados de forma satisfatória. Assim, se o grupo tirar 10 no projeto e ainda
ganhar o bônus de 3,34, a média obtida será 13,34*30%= 4,00;
■ serão aceitos grupos de até 6 integrantes. Uma lista citando os integrantes do
grupo deverá ser entregue em sala de aula até o dia 09/11/ 16 ;
■ o trabalho deverá ser apresentado dia 09/12 (qualquer GRAD) e todos os
integrantes deverão participar;
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
■ Se o software está realmente funcional;
■ Se os critérios mínimos definidos foram atendidos;
■ A qualidade da apresentação (não é preciso fazer ppt);
■ A qualidade da implementação;
■ A qualidade do relatório;
■ Acompanhamento do projeto pelo monitor do grupo;
■ A presença e participação na apresentação do trabalho;
■ A participação no desenvolvimento do trabalho.

## 4. Dicas

■ Não deixe para começar o projeto mais tarde. Comece logo e no mais tardar dia
04 / 11 /201 6 :-)
■ Impossível fazer o projeto de “virada” ... mesmo em duas semanas de “viradas” ;-)
■ Fazer um cronograma de atividades de desenvolvimento do projeto. Considere
que está na época de provas e potencialmente de desenvolvimento de outros
projetos em outras disciplinas;
■ Começar o trabalho definindo os protocolos de comunicação e planejando como
será a implementação. Assim, fica mais fácil saber o que implementar e como. O
tempo de implementação também é minimizado;
■ Não esquecer de dar atenção ao relatório! A entrega do mesmo deverá ser no dia
da apresentação, mas antes de começá-la;
■ Os monitores acompanharão o desenvolvimento dos projetos. O monitor do seu
grupo será definido até dia 11 / 11. Por isso, não esqueça de entregar em sala a
lista com o nome dos componentes de seu grupo;
■ Haverá 03 marcos de acompanhamento do projeto: 1º até 2 5 / 11 , 2º até 02 / 12 e 3º
até 07 / 12. Isto significa que até cada uma dessas datas o grupo deverá mostrar ao
monitor responsável pela equipe o status atual de desenvolvimento do projeto.
■ A responsabilidade de ter acompanhamento no projeto é do grupo e não do
monitor. Assim, não espere que os monitores vão “correr atrás” dos grupos.
■ Cuidado ao dividir as tarefas de implementação do projeto entre os componentes
do grupo. Será necessário integrar tudo depois e isso leva tempo (tipicamente não
menos de 1 semana a 2 semanas a depender da equipe)!
■ Lembrem-se da importância do projeto! São até 3 pontos na média parcial (ou 4
com o bônus)!
■ Certamente a especificação apresentada deixa pontos em aberto. Dúvidas?
Pergunte em sala! Suas dúvidas podem ser também as de outros alunos!


