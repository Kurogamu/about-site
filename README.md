# about-site

A simple virtual business card. Let's me avoid relying on other pages to share my resume.

It's a fairly usable starting point for a bare-bones cljs + reagent + sass static site.

## Development

Requires JDK for ClojureScript and npm for building.
I use the following NixOS packages

```
pkgs.nodejs_22
pkgs.jdk21_headless
pkgs.clojure
pkgs.clojure-lsp
pkgs.leiningen
```

After running `npm install`, run auto-reloading cljs and sass services with the following

```
npm run app
npm run sass
```

## Deploying

The Dockerfile generates a minified site in `/app/public`, making it ready for DigitalOcean static distribution.
