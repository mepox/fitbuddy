# Contributing guidelines

Contributions are welcome. 👍 

Please follow the guide described below how can you contribute to this project.

## Submitting Issues

- Before submitting any new issues, please make sure there are no similar issues has been opened already. [See all issues in the project](https://github.com/mepox/fitbuddy/issues)
- Please use the GitHub web interface to create and submit a new issue. [Submit a new issue here](https://github.com/mepox/fitbuddy/issues/new)
- The following issue types are welcome (for now): 
  - 🐛 bugs: Have you found bugs in the project that no one noticed yet? 
  - ✍️ typos: Have you noticed a typo in the project? 
  - 📰 docs: Documents are never enough and can be always improved.
  - 💼 code: The code is still WIP, but if you have found something that could be done better then feel free to report it. Feedbacks are always welcome!
  - 🔆 ideas: Ideas are welcome, just please note the project still in WIP.
  - ❓ question: Have a question? Feel free to open a new issue.

## Working on Issues

- Issues open for contributions are usually labelled with `help wanted`. [See all help wanted issues in the project](https://github.com/mepox/fitbuddy/labels/help%20wanted)
- Before you start working on an issue, please make sure you did one of the following:
  - Ask to get assigned first. When someone got assigned to an issue then it's very clear for everyone who is working on it!
  - Ask or at least let everyone know in the comments that you are working on the issue.

❗ *By not following these simple rules could lead to confusion and disappointment!* ❗

## Submitting Pull Requests

- Before you submit your PR, it's highly encouraged to run `mvn test` to make sure all tests are green.
- When you are ready with your work, please use the GitHub web interface to submit your PR.
- For a better workflow, please submit one PR for each issue. (You should only really work on one issue at a time)
  - PR title: Usually it's the same or similar to the issue title.
  - PR description: 
    - Summary: Please fill in a brief summary.
    - Linking an issue: At the bottom, please add a closing keyword and the issue number, for example `Close #123`. [See GitHub Docs](https://docs.github.com/en/issues/tracking-your-work-with-issues/linking-a-pull-request-to-an-issue)
- After you submitted your PR, an automated code quality check goes through on your PR content to make sure it follows the coding convention.
- Then a project member will review your PR.
- If your PR got approved then congratulations 🎉 your PR will be merged soon! You will be also added to the hall of fame 🚀
- If changes are requested don't panic. Complete the required changes and re-submit your PR.

❗ *By not following these simple rules could lead to confusion and disappointment!* ❗

## Project uses the following styles

- Commits: Moved away from conventional commits, so use standard commit messages:
  - The subject line must not exceed 50 characters
  - The subject line should be capitalized and must not end in a period
  - The subject line must be written in imperative mood (Fix, not Fixed / Fixes etc.)
 
- Indentation: Size 4 with Tabs only policy.

## Lombok

The project uses Lombok. If you see lots of `method is undefined` errors in your freshly forked project that means you might need to install Lombok plugin to your IDE.

#### Instructions how to install the Lombok plugin to your IDE:

Eclipse: https://projectlombok.org/setup/eclipse

IntelliJ IDEA: https://projectlombok.org/setup/intellij

Netbeans: https://projectlombok.org/setup/netbeans

Visual Studio Code: https://projectlombok.org/setup/vscode

## Thank you ♥️

Thank you for investing your time in contributing to this project! ♥️
