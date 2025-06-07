let
  pkgs = import <nixpkgs> {};
in pkgs.mkShell {
  packages = [
    pkgs.clojure
    pkgs.clojure-lsp
    pkgs.leiningen
    pkgs.nodejs_22
    pkgs.jdk21_headless
  ];
}

