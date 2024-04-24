package banco;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Cliente implements Usuario{
    private String nomeCliente;
    private String sobrenomeCliente;
    private String cpfCliente;
    private String senhaCliente;
    private double saldoCliente;

    Scanner scan = new Scanner(System.in);
    List<String> arrNome = new ArrayList<>();
    List<String> arrSobrenome = new ArrayList<>();
    List<String> arrCpf = new ArrayList<>();
    List<String> arrSenha = new ArrayList<>();

    public Cliente() {
        this.setNomeCliente(null);
        this.setSobrenomeCliente(null);
        this.setCpfCliente(null);
        this.setSenhaCliente(null);
        this.setSaldoCliente(0.0);
    }

    public void chamarInicio() {
        System.out.println("""
                ------ Bem-vindo ao UnoBank ------
                Se já é um cliente, digite 0
                Se deseja criar uma conta, digite 1
                ----------------------------------
                """);
    }

    public void chamarOpcoes() throws InterruptedException {
        System.out.println("""
                ------ UnoBank ------
                Se desejar sacar, digite 0
                Se deseja depositar, digite 1
                Se deseja consultar seu saldo, digite 2
                Caso queira encerrar o atendimento, digite 3
                ---------------------
                """);
        chamarSouCliente();
    }

    public void chamarSouCliente() throws InterruptedException {
        int digito = scan.nextInt();
        scan.nextLine();
        switch (digito) {
            case 0:
                sacarDinheiro();
                Thread.sleep(1000);
                chamarOpcoes();
                break;
            case 1:
                depositarDinheiro();
                Thread.sleep(1000);
                chamarOpcoes();
                break;
            case 2:
                consultarDinheiro();
                Thread.sleep(1000);
                chamarOpcoes();
                break;
            case 3:
                System.out.println("Atendimento encerrado");
                break;
        }
    }

    public void chamarNaoSouCliente() throws InterruptedException {
        System.out.println("Ótimo que deseja se tornar nosso cliente!");
        Thread.sleep(1000);
        registrarUsuario();
        System.out.println("""
                    Usuário registrado com sucesso!
                    Você será redirecionado à página inicial...""");
        Thread.sleep(1000);
        realizarAtendimento();
    }

    public void realizarAtendimento() throws InterruptedException {
        chamarInicio();
        int digito = scan.nextInt();
        scan.nextLine();
        if (digito == 0) {
            validarUsuario();
            Thread.sleep(1000);
            chamarOpcoes();
        } else if (digito == 1) {
            chamarNaoSouCliente();
        }
    }

    @Override
    public void sacarDinheiro() {
        System.out.println("Digite quanto deseja sacar:");
        double valor = scan.nextDouble();
        scan.nextLine();
        if (valor <= getSaldoCliente()) {
            this.setSaldoCliente(getSaldoCliente() - valor);
            System.out.println("Você sacou: " + valor + "R$");
        } else {
            System.out.println("Saldo insuficiente.");
        }
    }

    @Override
    public void depositarDinheiro() {
        System.out.println("Digite quanto deseja depositar:");
        double valor = scan.nextDouble();
        scan.nextLine();
        setSaldoCliente(valor + saldoCliente);
        System.out.println("Você depositou: " + valor + "R$");
    }

    @Override
    public void consultarDinheiro() {
        System.out.println("Seu saldo é de: " + getSaldoCliente() + "R$");
    }

    @Override
    public void validarUsuario() {
        boolean usuarioEncontrado = false;

        while (!usuarioEncontrado) {
            System.out.print("Digite seu CPF (ou 'sair' para encerrar): ");
            String cpfValidacao = scan.next();

            if (cpfValidacao.equalsIgnoreCase("sair")) {
                break;
            }
            for (int i = 0; i < arrCpf.size(); i++) {
                if (arrCpf.get(i).equalsIgnoreCase(cpfValidacao)) {
                    usuarioEncontrado = true;
                    boolean senhaValida = false;
                    while (!senhaValida) {
                        System.out.print("Digite sua senha: ");
                        String senhaValidacao = scan.next();
                        if (arrSenha.get(i).equalsIgnoreCase(senhaValidacao)) {
                            System.out.println("Bem-vindo " + arrNome.get(i));
                            senhaValida = true;
                        } else {
                            System.out.println("Senha incorreta!");
                        }
                    }
                    break;
                }
            }

            if (!usuarioEncontrado) {
                System.out.println("Usuário não encontrado.");
            }
        }
    }

    @Override
    public void registrarUsuario() {
        while (true) {
            System.out.println("""
                    ------ UnoBank ------
                    Digite seu primeiro nome:
                    ---------------------
                    """);
            String fNome = scan.next();
            if (fNome.matches("[a-zA-Z]+")) {
                this.setNomeCliente(fNome);
                arrNome.add(getNomeCliente());
                break;
            } else {
                System.out.println("Digite apenas letras!");
            }
        }

        while (true) {
            System.out.println("""
                    ------ UnoBank ------
                    Digite seu sobrenome:
                    ---------------------""");
            String lNome = scan.next();
            if (lNome.matches("[a-zA-Z]+")) {
                this.setSobrenomeCliente(lNome);
                arrSobrenome.add(getSobrenomeCliente());
                break;
            } else {
                System.out.println("Digite apenas letras!");
            }
        }

        while (true) {
            System.out.println("""
                    ------ UnoBank ------
                    Digite seu cpf sem traços ou pontos:
                    ---------------------""");
            String nCpf = scan.next();

            boolean cpfExiste = false;
            for (String c : arrCpf) {
                if (c.equalsIgnoreCase(nCpf)) {
                    cpfExiste = true;
                    break;
                }
            }

            if (!cpfExiste) {
                if (nCpf.matches("[0-9]+")) {
                    if (nCpf.length() == 11) {
                        this.setCpfCliente(nCpf);
                        arrCpf.add(getCpfCliente());
                        break;
                    } else {
                        System.out.println("Estão faltando dígitos no seu CPF!");
                    }
                } else {
                    System.out.println("Digite apenas números!");
                }
            } else {
                System.out.println("Este CPF já está cadastrado!");
            }
        }

        while (true) {
            System.out.println("""
                    ------ UnoBank ------
                    Digite sua senha:
                    ---------------------""");
            String uSenha = scan.next();
            if (6 <= uSenha.length() && uSenha.length() <= 10) {
                this.setSenhaCliente(uSenha);
                arrSenha.add(getSenhaCliente());
                break;
            } else {
                System.out.println("Senha fraca! A senha deve conter entre 6 e 10 caracteres.");
            }
        }
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getSobrenomeCliente() {
        return sobrenomeCliente;
    }

    public void setSobrenomeCliente(String sobrenomeCliente) {
        this.sobrenomeCliente = sobrenomeCliente;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public String getSenhaCliente() {
        return senhaCliente;
    }

    public void setSenhaCliente(String senhaCliente) {
        this.senhaCliente = senhaCliente;
    }

    public double getSaldoCliente() {
        return saldoCliente;
    }

    public void setSaldoCliente(Double saldoCliente) {
        this.saldoCliente = saldoCliente;
    }

}
