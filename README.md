# tec502-pbl1-monitoring

<p align="center">
  <img src="https://i.imgur.com/ZCXxpeh.png" alt="Monitoring" width="650px" height="450px">
</p>

## 📖 Descrição do Projeto ##
> **Resolução do problema 1 do MI - Concorrência e Conectividade (TEC 502).**<br/><br/>
O projeto trata-se de um dos *clients* que se conecta ao [servidor](https://github.com/AllanCapistrano/tec502-pbl1-server) principal, e tem como função exibir os dados, que são medidos pelos sensores e processados no servidor, para os médicos poderem acompanhar a situação em tempo real dos pacientes que estão utilizando o dispositivo de monitoramento de COVID-19.<br/>
Este *client* se comunica com o servidor utilizando um protocolo baseado em [API REST](https://www.redhat.com/pt-br/topics/api/what-is-a-rest-api), com requisições [HTTP](https://developer.mozilla.org/pt-BR/docs/Web/HTTP/Methods) e mensagens no formato [JSON](https://www.json.org/json-en.html). Essas requisições são feitas para o servidor a cada 5 segundos por uma *thread*, garantindo dessa forma, um acompanhamento em tempo real dos dados por um profissional da saúde.

### ⛵ Navegação pelos projetos: ###
- [Servidor](https://github.com/AllanCapistrano/tec502-pbl1-server)
- [Emulador de Sensores](https://github.com/AllanCapistrano/tec502-pbl1-sensors)
- \> Monitoramento de Pacientes
- [API REST](https://github.com/AllanCapistrano/tec502-pbl1-api)

### 📂 Tecnologias utilizadas: ### 
- [Java JDK 8](https://www.oracle.com/br/java/technologies/javase/javase-jdk8-downloads.html)
- [Scene Builder](https://gluonhq.com/products/scene-builder/)

### 📦 Dependências: ### 
- [JSON](https://www.json.org/json-en.html)

------------

## 💻 Como utilizar ##

### Compilar o projeto ###
1. Para o utilizar este projeto é necessário ter instalado o JDK 8u111.

- [JDK 8u111 com Netbeans 8.2](https://www.oracle.com/technetwork/java/javase/downloads/jdk-netbeans-jsp-3413139-esa.html)
- [JDK 8u111](https://www.oracle.com/br/java/technologies/javase/javase8-archive-downloads.html)

2. Após instalado, basta executar o projeto na sua IDE de escolha.

### Através de arquivos já gerados ###
1. Caso esteja utilizando o sistema operacional **Windows**, [clique aqui](https://github.com/AllanCapistrano/tec502-pbl1-monitoring/releases/tag/v1.1) e faça o download do arquivo `.exe` ou `.jar`;
2. Porém, caso esteja utilizando o sistema operacional **macOS** ou alguma distribuição **Linux**, [clique aqui](https://github.com/AllanCapistrano/tec502-pbl1-monitoring/releases/tag/v1.1) e faça o download do arquivo `.jar`;
3. Após isso, com o [servidor](https://github.com/AllanCapistrano/tec502-pbl1-server) *online*, basta instalar o programa através do arquivo `.exe` ou executar o arquivo `.jar`.

###### Obs1: Utilizando o arquivo `.exe` não é necessário ter o JDK 8u111 instalado. ######
###### Obs2: Para executar o arquivo `.jar` é necessário ter o JDK 8u111 instalado. ######

------------

## 👨‍💻 Autor ##

| [![Allan Capistrano](https://github.com/AllanCapistrano.png?size=100)](https://github.com/AllanCapistrano) |
| -----------------------------------------------------------------------------------------------------------|
| [Allan Capistrano](https://github.com/AllanCapistrano)                                                     |

<p>
    <h3>Onde me encontrar:</h3>
    <a href="https://github.com/AllanCapistrano">
        <img src="https://github.com/AllanCapistrano/AllanCapistrano/blob/master/assets/github-square-brands.png" alt="Github icon" width="5%">
    </a>
    &nbsp
    <a href="https://www.linkedin.com/in/allancapistrano/">
        <img src="https://github.com/AllanCapistrano/AllanCapistrano/blob/master/assets/linkedin-brands.png" alt="Linkedin icon" width="5%">
    </a> 
    &nbsp
    <a href="https://mail.google.com/mail/u/0/?view=cm&fs=1&tf=1&source=mailto&to=asantos@ecomp.uefs.br">
        <img src="https://github.com/AllanCapistrano/AllanCapistrano/blob/master/assets/envelope-square-solid.png" alt="Email icon" width="5%">
    </a>
</p>

------------

## ⚖️ Licença ##
[GPL-3.0 License](https://github.com/AllanCapistrano/tec502-pbl1-monitoring/blob/main/LICENSE)
